package com.book_reading.mapper;

import com.book_reading.dto.request.BookCreationRequest;
import com.book_reading.dto.request.BookUpdateRequest;
import com.book_reading.dto.response.BookDetailResponse;
import com.book_reading.dto.response.BookHotResponse;
import com.book_reading.dto.response.BookResponse;
import com.book_reading.entity.Book;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AuthorMapperHelper.class, GenreMapperHelper.class})
public interface BookMapper {
    BookResponse toBookResponse(Book book);

    BookDetailResponse toBookDetailResponse(Book book);

    List<BookResponse> toListBookResponse(List<Book> book);
    List<BookHotResponse> toListBookHotResponse(List<Book> book);
    List<BookHotResponse> toListBookByGenreResponse(List<Book> book);

    default Page<BookResponse> toPageBookResponse(Page<Book> book) {
        return book.map(this::toBookResponse);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "author", target = "author")
    @Mapping(source = "genre", target = "genre")
    void toUpdateBook(BookUpdateRequest request,@MappingTarget Book book);
}
