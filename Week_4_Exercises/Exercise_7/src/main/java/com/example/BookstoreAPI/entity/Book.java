package com.example.BookstoreAPI.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private int id;
    private String title;
    private String author;
    private double price;
    private String isbn;
}
// Example jsons for posting into books
/* 
    {
        "id": 1,
        "title": "Example Book Title",
        "author": "Example Author",
        "price": 19.99,
        "isbn": "123-4567890123"
    },
    {
        "id": 2,
        "title": "Effective Java",
        "author": "Joshua Bloch",
        "price": 45.0,
        "isbn": "978-0134685991"
    },
    {
        "id": 3,
        "title": "Clean Code",
        "author": "Robert C. Martin",
        "price": 40.0,
        "isbn": "978-0132350884"
    },
    {
        "id": 4,
        "title": "The Pragmatic Programmer",
        "author": "Andrew Hunt and David Thomas",
        "price": 50.0,
        "isbn": "978-0201616224"
    },
    {
        "id": 5,
        "title": "Design Patterns: Elements of Reusable Object-Oriented Software",
        "author": "Erich Gamma, Richard Helm, Ralph Johnson, and John Vlissides",
        "price": 55.0,
        "isbn": "978-0201633610"
    },

 */