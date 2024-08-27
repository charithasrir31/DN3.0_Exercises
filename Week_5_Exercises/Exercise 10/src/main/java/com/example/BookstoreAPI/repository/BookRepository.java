package com.example.BookstoreAPI.repository;

import com.example.BookstoreAPI.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
/*
    List<Book> findByTitleContaining(String title);

    List<Book> findByAuthor_NameContaining(String authorName); // Updated to match field name in Author entity

    List<Book> findByTitleContainingAndAuthor_NameContaining(String title, String authorName); // Updated field name
 */
}
