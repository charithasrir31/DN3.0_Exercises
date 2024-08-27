package com.example.bookstoreapi.service;

import com.example.bookstoreapi.model.Book;
import com.example.bookstoreapi.resource.BookResource;
import com.example.bookstoreapi.resource.BookResourceAssembler;
import com.example.bookstoreapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookResourceAssembler bookResourceAssembler;

    public Book createBook(Book book) {
        Book savedBook = bookRepository.save(book);
        return bookResourceAssembler.toModel(savedBook).getBook();
    }

    public Optional<BookResource> getBook(Long id) {
        return bookRepository.findById(id)
                .map(bookResourceAssembler::toModel);
    }

    public BookResource updateBook(Long id, Book book) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found");
        }
        book.setId(id);
        Book updatedBook = bookRepository.save(book);
        return bookResourceAssembler.toModel(updatedBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookResource> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookResourceAssembler::toModel)
                .collect(Collectors.toList());
    }
}
