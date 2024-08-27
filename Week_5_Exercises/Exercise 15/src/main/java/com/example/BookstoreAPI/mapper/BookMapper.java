package com.example.BookstoreAPI.mapper;

import com.example.BookstoreAPI.dto.BookDTO;
import com.example.BookstoreAPI.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO toDTO(Book book);
    Book toEntity(BookDTO bookDTO);
}

