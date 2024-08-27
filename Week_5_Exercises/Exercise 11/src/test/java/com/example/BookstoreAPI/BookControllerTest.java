package com.example.BookstoreAPI;

import com.example.BookstoreAPI.model.Book;
import com.example.BookstoreAPI.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void testCreateBook() throws Exception {
        Book book = new Book("1234567890123", "The Alchemist", "Paulo Coelho", 300);
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"isbn\":\"1234567890123\", \"title\":\"The Alchemist\", \"author\":\"Paulo Coelho\", \"price\":300}")
                )
                .andExpect(MockMvcResultMatchers.status().isCreated()) // Expect 201 Created
                .andExpect(jsonPath("$.title").value("The Alchemist"))
                .andExpect(jsonPath("$.author").value("Paulo Coelho"))
                .andExpect(jsonPath("$.price").value(300));
    }

    @Test
    public void testGetBook() throws Exception {
        Book book = new Book("1234567890123", "The Alchemist", "Paulo Coelho", 300);
        Mockito.when(bookRepository.findById(Long.valueOf("1234567890123"))).thenReturn(Optional.of(book));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/1234567890123"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.isbn").value("1234567890123"))
                .andExpect(jsonPath("$.title").value("The Alchemist"))
                .andExpect(jsonPath("$.author").value("Paulo Coelho"))
                .andExpect(jsonPath("$.price").value(300))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.update.href").exists())
                .andExpect(jsonPath("$._links.delete.href").exists());
    }

    @Test
    public void testUpdateBook() throws Exception {

        Book existingBook = new Book("1234567890123", "Old Title", "Old Author", 300);
        Book updatedBook = new Book("1234567890123", "The Alchemist", "Paulo Coelho", 350);

        Mockito.when(bookRepository.findById(Long.valueOf("1234567890123"))).thenReturn(Optional.of(existingBook));
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(updatedBook);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/1234567890123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"isbn\":\"1234567890123\", \"title\":\"The Alchemist\", \"author\":\"Paulo Coelho\", \"price\":350}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.isbn").value("1234567890123"))
                .andExpect(jsonPath("$.title").value("The Alchemist"))
                .andExpect(jsonPath("$.author").value("Paulo Coelho"))
                .andExpect(jsonPath("$.price").value(350));
    }

    @Test
    public void testDeleteBook() throws Exception {
        Book book = new Book("1234567890123", "The Alchemist", "Paulo Coelho", 300);

        Mockito.when(bookRepository.findById(Long.valueOf("1234567890123"))).thenReturn(Optional.of(book));
        Mockito.doNothing().when(bookRepository).delete(Mockito.any(Book.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/1234567890123"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
