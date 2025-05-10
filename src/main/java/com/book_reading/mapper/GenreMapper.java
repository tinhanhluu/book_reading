package com.book_reading.mapper;

import com.book_reading.dto.response.GenreResponse;
import com.book_reading.entity.Genre;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreResponse toGenreResponse(Genre genre);
    List<GenreResponse> toAllGenreResponse(List<Genre> genres);
}
