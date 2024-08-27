package com.example.BookstoreAPI.assembler;

import com.example.BookstoreAPI.controller.CustomerController;
import com.example.BookstoreAPI.model.Customer;
import com.example.BookstoreAPI.resource.CustomerResource;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class CustomerResourceAssembler implements RepresentationModelAssembler<Customer, CustomerResource> {

    @Override
    public CustomerResource toModel(Customer customer) {
        return new CustomerResource(customer);
    }
}
