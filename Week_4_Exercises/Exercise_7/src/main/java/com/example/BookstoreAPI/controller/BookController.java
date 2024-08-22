package com.example.BookstoreAPI.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.BookstoreAPI.entity.Book;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@RequestMapping("/books")
public class BookController {

    private List<Book> books = new ArrayList<>();

    // GET request to retrieve all books
    @GetMapping
    public List<Book> getAllBooks() {
        return books;
    }

    // POST request to add a new book
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@RequestBody Book book) {
        books.add(book);
        return book;
    }

    // PUT request to update an existing book
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
        for (Book book : books) {
            if (book.getId() == id) {
                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
                book.setPrice(updatedBook.getPrice());
                book.setIsbn(updatedBook.getIsbn());
                return book;
            }
        }
        return null;
    }

    // DELETE request to remove a book
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable int id) {
        boolean removed = books.removeIf(book -> book.getId() == id);
        if (!removed) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Book not found");
        }
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) {
        return books.stream()
            .filter(book->book.getId()==id)
            .findFirst()
            .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam(required = false) String title,
                                  @RequestParam(required = false) String author) {
        return books.stream()
            .filter(book -> (title == null || book.getTitle().equalsIgnoreCase(title)) &&
            (author == null || book.getAuthor().equalsIgnoreCase(author)))
            .toList();
    }
    
    @GetMapping("/customheader/{id}")
    public ResponseEntity<Book> getBookId(@PathVariable int id) {
        Book book = books.stream()
                    .filter(b->b.getId() == id)
                    .findFirst()
                    .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "CustomValue");
        return ResponseEntity.ok()
            .headers(headers)
            .body(book);
    }
}
