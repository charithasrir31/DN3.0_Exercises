package com.example.BookstoreAPI;

import com.example.BookstoreAPI.assembler.BookResourceAssembler;
import com.example.BookstoreAPI.controller.BookController;
import com.example.BookstoreAPI.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(BookController.class)
@Import(BookResourceAssembler.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @Test
    void testInternalServerErrorException() throws Exception {
        Mockito.when(bookRepository.findById(2L)).thenThrow(new RuntimeException("Internal error"));

        mockMvc.perform(get("/api/books/2"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("An error occurred"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
    @Test
    void testMethodNotAllowedException() throws Exception {
        mockMvc.perform(post("/api/books/1"))
                // Expect 405 Method Not Allowed
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Checks the message field
                .andExpect(jsonPath("$.message").value("Method Not Allowed"))
                // Checks the timestamp field
                .andExpect(jsonPath("$.timestamp").exists())
                // Checks the details field
                .andExpect(jsonPath("$.details").exists());
    }

}
