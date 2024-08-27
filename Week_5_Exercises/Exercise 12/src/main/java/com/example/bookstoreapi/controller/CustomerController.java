package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.model.Customer;
import com.example.bookstoreapi.resource.CustomerResource;
import com.example.bookstoreapi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResource> createCustomer(@Valid @RequestBody Customer customer) {
        CustomerResource customerResource = customerService.createCustomer(customer);
        return new ResponseEntity<>(customerResource, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResource> getCustomer(@PathVariable Long id) {
        Optional<CustomerResource> customerResource = customerService.getCustomer(id);
        return customerResource.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResource> updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customer) {
        CustomerResource customerResource = customerService.updateCustomer(id, customer);
        return new ResponseEntity<>(customerResource, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Iterable<CustomerResource>> getAllCustomers() {
        // Implement to return a collection of CustomerResource
        return null;
    }
}
