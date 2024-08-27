package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.metrics.CustomMetrics;
import com.example.bookstoreapi.model.Book;
import com.example.bookstoreapi.repository.BookRepository;
import com.example.bookstoreapi.service.BookService;
import com.example.bookstoreapi.resource.BookResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final CustomMetrics customMetrics;
    private final BookRepository bookRepository;

    public BookController(BookService bookService, CustomMetrics customMetrics, BookRepository bookRepository) {
        this.bookService = bookService;
        this.customMetrics = customMetrics;
        this.bookRepository = bookRepository;
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody @Valid Book book) {
        Book createdBook = bookService.createBook(book);
        customMetrics.incrementBookCreation();  // Increment the custom metric
        return ResponseEntity.ok(createdBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResource> getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(book -> {
                    BookResource bookResource = new BookResource(book);
                    return new ResponseEntity<>(bookResource, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<BookResource>> getAllBooks(@RequestParam(required = false) String mediaType) {
        List<Book> books = bookRepository.findAll();
        MediaType mediaTypeToUse = (mediaType != null && mediaType.equals("xml")) ?
                MediaType.APPLICATION_XML : MediaType.APPLICATION_JSON;
        List<BookResource> bookResources = books.stream()
                .map(BookResource::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().contentType(mediaTypeToUse).body(bookResources);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        if (!bookRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        book.setId(id);
        Book updatedBook = bookRepository.save(book);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (!bookRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
