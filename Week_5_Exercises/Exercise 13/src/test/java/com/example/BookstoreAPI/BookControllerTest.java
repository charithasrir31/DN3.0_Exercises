package com.example.BookstoreAPI;

import com.example.BookstoreAPI.assembler.BookResourceAssembler;
import com.example.BookstoreAPI.controller.BookController;
import com.example.BookstoreAPI.model.Book;
import com.example.BookstoreAPI.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private BookResourceAssembler bookResourceAssembler;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetBookById() throws Exception {
        Book book = new Book("1234567890", "Title", "Author", 20);
        given(bookRepository.findById("1234567890")).willReturn(Optional.of(book));

        mockMvc.perform(get("/books/{isbn}", "1234567890"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("1234567890"))
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.author").value("Author"))
                .andExpect(jsonPath("$.price").value(20));
    }

    @Test
    void testCreateBook() throws Exception {
        Book book = new Book("1234567890", "Title", "Author", 20);
        given(bookRepository.save(book)).willReturn(book);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isbn").value("1234567890"))
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.author").value("Author"))
                .andExpect(jsonPath("$.price").value(20));
    }

    @Test
    void testUpdateBook() throws Exception {
        Book book = new Book("1234567890", "Title", "Author", 20);
        given(bookRepository.findById("1234567890")).willReturn(Optional.of(book));
        given(bookRepository.save(book)).willReturn(book);

        mockMvc.perform(put("/books/{isbn}", "1234567890")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("1234567890"))
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.author").value("Author"))
                .andExpect(jsonPath("$.price").value(20));
    }

    @Test
    void testDeleteBook() throws Exception {
        Book book = new Book("1234567890", "Title", "Author", 20);
        given(bookRepository.findById("1234567890")).willReturn(Optional.of(book));

        mockMvc.perform(delete("/books/{isbn}", "1234567890"))
                .andExpect(status().isNoContent());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
