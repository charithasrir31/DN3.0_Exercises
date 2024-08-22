package com.example.BookstoreAPI.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private int id;

    @JsonProperty("book_title")
    private String title;
    private String author;
    private double price;

    @JsonProperty("isbn_number")
    private String isbn;

    @JsonProperty("published_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String publishedDate;
}
