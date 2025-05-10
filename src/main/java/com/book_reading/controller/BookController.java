package com.book_reading.controller;

import com.book_reading.dto.request.BookCreationRequest;
import com.book_reading.dto.request.BookUpdateRequest;
import com.book_reading.dto.response.ApiResponse;
import com.book_reading.dto.response.BookResponse;
import com.book_reading.service.BookService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookController {
    BookService bookService;

    @PostMapping
    public ApiResponse<BookResponse> createBook(BookCreationRequest request){
        return ApiResponse.<BookResponse>builder()
                .message("Book creation successful")
                .data(bookService.createBook(request))
                .build();
    }

    @GetMapping("/{bookId}")
    public ApiResponse<BookResponse> getAuthorById(@PathVariable String bookId) {
        return ApiResponse.<BookResponse>builder()
                .data(bookService.getBookById(bookId))
                .build();
    }

    @GetMapping
    public ApiResponse<List<BookResponse>> getAllBooks() {
        return ApiResponse.<List<BookResponse>>builder()
                .message("Get all books successful")
                .data(bookService.getAllBooks())
                .build();
    }
    @GetMapping("/{genreId}/books")
    public ApiResponse<List<BookResponse>> getAllBooksByGenre(@PathVariable String genreId) {
        return ApiResponse.<List<BookResponse>>builder()
                .message("Get all books by genre successful")
                .data(bookService.getAllBooksByGenre(genreId))
                .build();
    }

    @GetMapping("/{authorId}/books")
    public ApiResponse<List<BookResponse>> getAllBooksByAuthor(@PathVariable String authorId) {
        return ApiResponse.<List<BookResponse>>builder()
                .message("Get all books by author successful")
                .data(bookService.getAllBooksByAuthor(authorId))
                .build();
    }

    @PatchMapping("/update/{bookId}")
    public ApiResponse<BookResponse> updateBook(@PathVariable String bookId, BookUpdateRequest request) {
        return ApiResponse.<BookResponse>builder()
                .message("Book update successful")
                .data(bookService.updateBook(bookId, request))
                .build();
    }

    @DeleteMapping("/{bookId}")
    public ApiResponse<?> deleteBook(@PathVariable String bookId) {
        bookService.deleteBook(bookId);
        return ApiResponse.builder()
                .message("Book deleted successful")
                .build();
    }
}
