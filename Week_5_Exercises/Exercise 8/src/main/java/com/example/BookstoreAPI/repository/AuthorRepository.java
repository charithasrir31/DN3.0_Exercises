// AuthorRepository.java
package com.example.BookstoreAPI.repository;

import com.example.BookstoreAPI.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
