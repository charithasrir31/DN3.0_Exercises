package com.example.BookstoreAPI;

import com.example.BookstoreAPI.assembler.BookResourceAssembler;
import com.example.BookstoreAPI.controller.BookController;
import com.example.BookstoreAPI.repository.BookRepository;
import com.example.BookstoreAPI.service.MetricsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

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

    @MockBean
    private MetricsService metricsService;  // Add this line to mock MetricsService

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
    public void testMethodNotAllowedException() throws Exception {
        mockMvc.perform(post("/api/books/1"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)); // Ensure the content type is correct.
    }

}
