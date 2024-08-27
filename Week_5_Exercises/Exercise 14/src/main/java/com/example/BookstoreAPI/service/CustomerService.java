package com.example.BookstoreAPI.service;

import com.example.BookstoreAPI.model.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private List<Customer> customers = new ArrayList<>();
    private long nextId = 1L;

    public Customer createCustomer(Customer customer) {
        customer.setId(nextId++);
        customers.add(customer);
        return customer;
    }

    public List<Customer> findAllCustomers() {
        return new ArrayList<>(customers);
    }

    public Customer findCustomerById(Long id) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Customer not found with ID " + id));
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer customer = findCustomerById(id);
        customer.setName(updatedCustomer.getName());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setVersion(updatedCustomer.getVersion());
        return customer;
    }

    public void deleteCustomer(Long id) {

        customers.removeIf(customer -> customer.getId().equals(id));
    }
}
