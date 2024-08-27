package com.example.BookstoreAPI.assembler;

import com.example.BookstoreAPI.controller.BookController;
import com.example.BookstoreAPI.resource.BookResource;
import com.example.BookstoreAPI.model.Book;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class BookResourceAssembler {

    // Method to assemble a Book into an EntityModel with links
    public EntityModel<Book> toEntityModel(Book book) {
        EntityModel<Book> bookModel = EntityModel.of(book);

        // Add a link to the BookController
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBook(book.getIsbn())).withSelfRel();
        bookModel.add(selfLink);

        return bookModel;
    }

    // Method to assemble a Book into a BookResource and then into an EntityModel
    public EntityModel<BookResource> toResourceModel(Book book) {
        BookResource bookResource = new BookResource(book);
        return EntityModel.of(bookResource);
    }
}
