package com.example.bookstoreapi.resource;

import com.example.bookstoreapi.controller.BookController;
import com.example.bookstoreapi.model.Book;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class BookResource extends RepresentationModel<BookResource> {

    private final Book book;

    public BookResource(Book book) {
        this.book = book;
        addLinks(); // Add links when creating the resource
    }

    public Book getBook() {
        return book;
    }

    public void addLinks() {
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(book.getId())).withSelfRel());
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getAllBooks(null)).withRel("books"));
    }
}
