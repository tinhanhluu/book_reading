package com.book_reading.mapper;

import com.book_reading.dto.request.BookCreationRequest;
import com.book_reading.dto.request.BookUpdateRequest;
import com.book_reading.dto.response.BookResponse;
import com.book_reading.entity.Book;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toBook(BookCreationRequest request);
    BookResponse toBookResponse(Book book);
    List<BookResponse> toListBookResponse(List<Book> book);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUpdateBook(BookUpdateRequest request,@MappingTarget Book book);
}
