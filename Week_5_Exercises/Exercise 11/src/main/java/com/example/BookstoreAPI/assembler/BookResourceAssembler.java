package com.example.BookstoreAPI.assembler;

import com.example.BookstoreAPI.controller.BookController;
import com.example.BookstoreAPI.model.Book;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class BookResourceAssembler {

    public EntityModel<Book> toModel(Book book) {
        EntityModel<Book> bookModel = EntityModel.of(book);

        Link selfLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(BookController.class).getBook(book.getIsbn())
        ).withSelfRel();

        Link updateLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(BookController.class).updateBook(book.getIsbn(), null)
        ).withRel("update");

        Link deleteLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(BookController.class).deleteBook(book.getIsbn())
        ).withRel("delete");

        bookModel.add(selfLink, updateLink, deleteLink);

        return bookModel;
    }
}
