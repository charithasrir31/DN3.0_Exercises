package com.example.BookstoreAPI.controller;

import com.example.BookstoreAPI.assembler.BookResourceAssembler;
import com.example.BookstoreAPI.model.Book;
import com.example.BookstoreAPI.repository.BookRepository;
import com.example.BookstoreAPI.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookResourceAssembler assembler;

    @Autowired
    private MetricsService metricsService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CollectionModel<EntityModel<Book>>> getAllBooks() {
        List<EntityModel<Book>> books = bookRepository.findAll().stream()
                .map(book -> {
                    metricsService.incrementBookAccessCount();  // Increment custom metric
                    return assembler.toModel(book);
                })
                .collect(Collectors.toList());

        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getAllBooks()).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(books, selfLink));
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<Book>> createBook(@RequestBody @Valid Book book) {
        Book savedBook = bookRepository.save(book);
        EntityModel<Book> bookModel = assembler.toModel(savedBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookModel);
    }

    @GetMapping(value = "/{isbn}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<Book>> getBook(@PathVariable String isbn) {
        Optional<Book> book = bookRepository.findById(Long.valueOf(isbn));
        return book.map(b -> {
                    metricsService.incrementBookAccessCount();  // Increment custom metric
                    return ResponseEntity.ok(assembler.toModel(b));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping(value = "/{isbn}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<Book>> updateBook(@PathVariable String isbn, @Valid @RequestBody Book updatedBook) {
        return bookRepository.findById(Long.valueOf(isbn))
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setPrice(updatedBook.getPrice());
                    Book updated = bookRepository.save(book);
                    return ResponseEntity.ok(assembler.toModel(updated));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{isbn}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Object> deleteBook(@PathVariable String isbn) {
        return bookRepository.findById(Long.valueOf(isbn))
                .map(book -> {
                    bookRepository.delete(book);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
