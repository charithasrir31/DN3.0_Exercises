package com.example.bookstoreapi.service;

import com.example.bookstoreapi.model.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private List<Customer> customers = new ArrayList<>();
    private long nextId = 1L;

    // Method to create a new customer
    public Customer createCustomer(Customer customer) {
        customer.setId(nextId++);
        customers.add(customer);
        return customer;
    }

    // Method to find all customers
    public List<Customer> findAllCustomers() {
        return new ArrayList<>(customers);
    }

    // Method to find a customer by ID
    public Customer findCustomerById(Long id) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Customer not found with ID " + id));
    }

    // Method to update a customer
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer customer = findCustomerById(id);
        customer.setName(updatedCustomer.getName());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setAddress(updatedCustomer.getAddress());
        return customer;
    }

    // Method to delete a customer
    public void deleteCustomer(Long id) {
        customers.removeIf(customer -> customer.getId().equals(id));
    }
}
