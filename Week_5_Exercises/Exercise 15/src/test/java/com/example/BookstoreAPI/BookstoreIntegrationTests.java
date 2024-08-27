package com.example.BookstoreAPI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookstoreIntegrationTests {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testGetBookByIdNotFound() throws Exception {
        mockMvc.perform(get("/books/999")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("user:password".getBytes())))
                .andExpect(status().isUnauthorized());
    }

}
