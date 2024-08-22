package com.example.BookstoreAPI.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.BookstoreAPI.entity.Customer;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private List<Customer> customers = new ArrayList<>();

    @GetMapping
    public List<Customer> getAllCustomers(){
        return customers;
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        customers.add(customer);
        return customer;
    }

    public Customer registerCustomer(@RequestParam String name,
                                     @RequestParam String email,
                                     @RequestParam String password) {
        Customer customer = new Customer(customers.size()+1,name,email,password);
        customers.add(customer);
        return customer;
    }
}
