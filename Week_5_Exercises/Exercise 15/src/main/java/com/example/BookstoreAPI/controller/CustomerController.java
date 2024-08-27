
package com.example.BookstoreAPI.controller;

import com.example.BookstoreAPI.model.Customer;
import com.example.BookstoreAPI.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<EntityModel<Customer>> createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        EntityModel<Customer> customerResource = EntityModel.of(createdCustomer,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(createdCustomer.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getAllCustomers()).withRel("customers"));

        return ResponseEntity.created(customerResource.getRequiredLink("self").toUri()).body(customerResource);
    }

    @GetMapping
    public ResponseEntity<List<EntityModel<Customer>>> getAllCustomers() {
        List<EntityModel<Customer>> customers = customerService.findAllCustomers().stream()
                .map(customer -> EntityModel.of(customer,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(customer.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getAllCustomers()).withRel("customers")))
                .collect(Collectors.toList());
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Customer>> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.findCustomerById(id);
        EntityModel<Customer> customerResource = EntityModel.of(customer,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getAllCustomers()).withRel("customers"));

        return ResponseEntity.ok(customerResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Customer>> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        Customer updated = customerService.updateCustomer(id, updatedCustomer);
        EntityModel<Customer> customerResource = EntityModel.of(updated,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(updated.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getAllCustomers()).withRel("customers"));

        return ResponseEntity.ok(customerResource);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}

