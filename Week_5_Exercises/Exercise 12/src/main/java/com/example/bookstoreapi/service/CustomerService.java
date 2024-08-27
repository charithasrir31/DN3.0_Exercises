package com.example.bookstoreapi.service;

import com.example.bookstoreapi.model.Customer;
import com.example.bookstoreapi.resource.CustomerResource;
import com.example.bookstoreapi.resource.CustomerResourceAssembler;
import com.example.bookstoreapi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerResourceAssembler customerResourceAssembler;

    public CustomerResource createCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        return customerResourceAssembler.toModel(savedCustomer);
    }

    public Optional<CustomerResource> getCustomer(Long id) {
        return customerRepository.findById(id)
                .map(customerResourceAssembler::toModel);
    }

    public CustomerResource updateCustomer(Long id, Customer customer) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found");
        }
        customer.setId(id);
        Customer updatedCustomer = customerRepository.save(customer);
        return customerResourceAssembler.toModel(updatedCustomer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
