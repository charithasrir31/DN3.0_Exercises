package com.example.bookstoreapi.resource;

import com.example.bookstoreapi.controller.CustomerController;
import com.example.bookstoreapi.model.Customer;
import com.example.bookstoreapi.resource.CustomerResource;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CustomerResourceAssembler extends RepresentationModelAssemblerSupport<Customer, CustomerResource> {

    public CustomerResourceAssembler() {
        super(CustomerController.class, CustomerResource.class);
    }

    @Override
    public CustomerResource toModel(Customer customer) {
        CustomerResource resource = new CustomerResource(customer);
        resource.addLinks();
        return resource;
    }
}
