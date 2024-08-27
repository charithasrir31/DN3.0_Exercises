package com.example.BookstoreAPI.resource;

import com.example.BookstoreAPI.controller.BookController;
import com.example.BookstoreAPI.model.Book;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

public class BookResource extends RepresentationModel<BookResource> {

    private final Book book;

    public BookResource(Book book) {
        this.book = book;
        addLinks();
    }

    public Book getBook() {
        return book;
    }

    public void addLinks() {
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBook(String.valueOf(book.getId()))).withSelfRel());
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getAllBooks()).withRel("books"));
    }
}
