package com.book_reading.mapper;

import com.book_reading.dto.request.AuthorCreationRequest;
import com.book_reading.dto.response.AuthorCreationResponse;
import com.book_reading.entity.Author;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toAuthor(AuthorCreationRequest request);
    AuthorCreationResponse toAuthorResponse(Author author);
    List<AuthorCreationResponse> toAllAuthorsResponse(List<Author> authors);
}
