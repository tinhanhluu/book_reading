package com.book_reading.mapper;

import com.book_reading.dto.request.BookCreationRequest;
import com.book_reading.dto.request.BookUpdateRequest;
import com.book_reading.dto.response.BookResponse;
import com.book_reading.entity.Book;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AuthorMapperHelper.class, GenreMapperHelper.class})
public interface BookMapper {
    BookResponse toBookResponse(Book book);
    List<BookResponse> toListBookResponse(List<Book> book);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "author", target = "author")
    @Mapping(source = "genre", target = "genre")
    void toUpdateBook(BookUpdateRequest request,@MappingTarget Book book);
}
