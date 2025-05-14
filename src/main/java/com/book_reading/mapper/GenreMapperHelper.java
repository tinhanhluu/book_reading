package com.book_reading.mapper;

import com.book_reading.entity.Genre;
import com.book_reading.exception.AppException;
import com.book_reading.exception.ErrorCode;
import com.book_reading.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenreMapperHelper {
    @Autowired
    private GenreRepository genreRepository;

    public Genre map(String genreId){
        return genreRepository.findById(genreId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
    }
}
