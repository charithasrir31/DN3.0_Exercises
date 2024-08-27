package com.example.BookstoreAPI.controller;

import com.example.BookstoreAPI.assembler.CustomerResourceAssembler;
import com.example.BookstoreAPI.model.Customer;
import com.example.BookstoreAPI.repository.CustomerRepository;
import com.example.BookstoreAPI.resource.CustomerResource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerResourceAssembler customerResourceAssembler;

    @PostMapping(consumes = MediaTypes.HAL_JSON_VALUE, produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CustomerResource> createCustomer(@Valid @RequestBody Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        CustomerResource customerResource = customerResourceAssembler.toModel(savedCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerResource);
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CustomerResource> getCustomer(@PathVariable Long id) {
        return customerRepository.findById(id)
                .map(customerResourceAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<CustomerResource>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerResource> customerResources = customers.stream()
                .map(customerResourceAssembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<CustomerResource> collectionModel = CollectionModel.of(customerResources);
        collectionModel.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(CustomerController.class).getAllCustomers())
                .withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @PutMapping(value = "/{id}", consumes = MediaTypes.HAL_JSON_VALUE, produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CustomerResource> updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer updatedCustomer) {
        return customerRepository.findById(id)
                .map(existingCustomer -> {
                    existingCustomer.setName(updatedCustomer.getName());
                    existingCustomer.setEmail(updatedCustomer.getEmail());
                    Customer savedCustomer = customerRepository.save(existingCustomer);
                    CustomerResource customerResource = customerResourceAssembler.toModel(savedCustomer);
                    return ResponseEntity.ok(customerResource);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
