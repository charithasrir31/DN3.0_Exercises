package com.example.BookstoreAPI.repository;

import com.example.BookstoreAPI.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}








