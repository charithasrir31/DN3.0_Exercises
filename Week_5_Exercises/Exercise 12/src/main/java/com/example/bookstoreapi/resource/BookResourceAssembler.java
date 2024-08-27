package com.example.bookstoreapi.resource;

import com.example.bookstoreapi.controller.BookController;
import com.example.bookstoreapi.model.Book;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class BookResourceAssembler extends RepresentationModelAssemblerSupport<Book, BookResource> {

    public BookResourceAssembler() {
        super(BookController.class, BookResource.class);
    }

    @Override
    public BookResource toModel(Book book) {
        BookResource resource = new BookResource(book);
        resource.addLinks(); // Now accessible
        return resource;
    }
}
