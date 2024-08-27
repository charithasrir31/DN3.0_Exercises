package com.example.BookstoreAPI.resource;

import com.example.BookstoreAPI.controller.CustomerController;
import com.example.BookstoreAPI.model.Customer;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public class CustomerResource extends RepresentationModel<CustomerResource> {

    private final Customer customer;

    public CustomerResource(Customer customer) {
        this.customer = customer;
        addLinks();
    }

    public Customer getCustomer() {
        return customer;
    }

    private void addLinks() {
        add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomer(customer.getId()))
                .withSelfRel());

        add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(CustomerController.class).getAllCustomers())
                .withRel("customers"));
    }
}
