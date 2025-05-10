package com.book_reading.controller;

import com.book_reading.dto.request.AuthorCreationRequest;
import com.book_reading.dto.response.ApiResponse;
import com.book_reading.dto.response.AuthorCreationResponse;
import com.book_reading.service.AuthorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorController {
    AuthorService authorService;

    @PostMapping
    public ApiResponse<AuthorCreationResponse> createAuthor(AuthorCreationRequest request) {
        return ApiResponse.<AuthorCreationResponse>builder()
                .data(authorService.createAuthor(request))
                .build();
    }

    @GetMapping("/{authorId}")
    public ApiResponse<AuthorCreationResponse> getAuthorById(@PathVariable String authorId) {
        return ApiResponse.<AuthorCreationResponse>builder()
                .data(authorService.getAuthorById(authorId))
                .build();
    }

    @GetMapping
    public ApiResponse<List<AuthorCreationResponse>> getAllAuthor() {
        return ApiResponse.<List<AuthorCreationResponse>>builder()
                .data(authorService.getAllAuthors())
                .build();
    }
}
