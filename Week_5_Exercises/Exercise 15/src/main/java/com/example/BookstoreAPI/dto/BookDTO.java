package com.example.BookstoreAPI.dto;
import org.springframework.hateoas.RepresentationModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BookDTO extends RepresentationModel<BookDTO> {
    private Long id;

    @JsonProperty("book_title")
    private String title;

    private String author;
    private Double price;

    @JsonIgnore
    private String internalCode;

    // Getters and setters

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getAuthor() {

        return author;
    }

    public void setAuthor(String author) {

        this.author = author;
    }

    public Double getPrice() {

        return price;
    }

    public void setPrice(Double price) {

        this.price = price;
    }

    public String getInternalCode() {

        return internalCode;
    }

    public void setInternalCode(String internalCode) {

        this.internalCode = internalCode;
    }
}
