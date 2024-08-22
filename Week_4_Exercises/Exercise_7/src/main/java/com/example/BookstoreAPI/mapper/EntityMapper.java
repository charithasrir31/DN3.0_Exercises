package com.example.BookstoreAPI.mapper;

import com.example.BookstoreAPI.dto.BookDTO;
import com.example.BookstoreAPI.dto.CustomerDTO;
import com.example.BookstoreAPI.entity.Book;
import com.example.BookstoreAPI.entity.Customer;

public interface EntityMapper {
    BookDTO toBookDto(Book book);
    Book toBook(BookDTO bookDTO);
    CustomerDTO toCustomerDto(Customer customer);
    Customer toCustomer(CustomerDTO customerDTO);
}
