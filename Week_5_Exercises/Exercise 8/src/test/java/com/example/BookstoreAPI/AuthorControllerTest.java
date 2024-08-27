package com.example.BookstoreAPI;

import com.example.BookstoreAPI.model.Author;
import com.example.BookstoreAPI.repository.AuthorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        authorRepository.deleteAll();
    }

    @Test
    public void testCreateAuthor() throws Exception {
        Author author = new Author();
        author.setName("Test Author");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/authors")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Author"));
    }

}
