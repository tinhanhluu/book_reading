package com.book_reading.controller;

import com.book_reading.dto.request.AuthorCreationRequest;
import com.book_reading.dto.request.GenreRequest;
import com.book_reading.dto.response.ApiResponse;
import com.book_reading.dto.response.AuthorCreationResponse;
import com.book_reading.dto.response.BookResponse;
import com.book_reading.dto.response.GenreResponse;
import com.book_reading.service.GenreService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genre")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class GenreController {
    GenreService genreService;

    @PostMapping
    public ApiResponse<GenreResponse> createGenre(GenreRequest request) {
        return ApiResponse.<GenreResponse>builder()
                .data(genreService.createGenre(request))
                .build();
    }

    @GetMapping("/{genreId}")
    public ApiResponse<GenreResponse> getAuthorById(@PathVariable String genreId) {
        return ApiResponse.<GenreResponse>builder()
                .data(genreService.getGenre(genreId))
                .build();
    }

    @GetMapping
    public ApiResponse<List<GenreResponse>> getAllGenres() {
        return ApiResponse.<List<GenreResponse>>builder()
                .message("Get all genres successful")
                .data(genreService.getAllGenres())
                .build();
    }
}
