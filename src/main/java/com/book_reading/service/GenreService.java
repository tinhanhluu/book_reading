package com.book_reading.service;

import com.book_reading.dto.request.GenreRequest;
import com.book_reading.dto.response.GenreResponse;
import com.book_reading.entity.Genre;
import com.book_reading.exception.AppException;
import com.book_reading.exception.ErrorCode;
import com.book_reading.mapper.GenreMapper;
import com.book_reading.repository.GenreRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GenreService {
    GenreRepository genreRepository;
    GenreMapper genreMapper;

    public GenreResponse createGenre(GenreRequest request){
        Genre genre = Genre.builder()
                .name(request.getName())
                .build();

        return genreMapper.toGenreResponse(genreRepository.save(genre));
    }

    public GenreResponse getGenre(String genreId){
        var genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));

        return genreMapper.toGenreResponse(genre);
    }

    public List<GenreResponse> getAllGenres(){
        return genreMapper.toAllGenreResponse(genreRepository.findAll());
    }

}
