package com.example.BookstoreAPI;

import com.example.BookstoreAPI.service.BookService;
import com.example.BookstoreAPI.model.Book;
import com.example.BookstoreAPI.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    public BookServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetBookById() {
        Book book = new Book();
        book.setIsbn(String.valueOf(1L));
        book.setTitle("The Book Title");
        book.setAuthor("The Author Name");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book foundBook = bookService.getBookById(1L);
        assertEquals("The Book Title", foundBook.getTitle());
    }
}
