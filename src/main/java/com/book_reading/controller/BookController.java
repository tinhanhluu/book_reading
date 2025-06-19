package com.book_reading.controller;

import com.book_reading.dto.request.BookCreationRequest;
import com.book_reading.dto.request.BookUpdateRequest;
import com.book_reading.dto.response.ApiResponse;
import com.book_reading.dto.response.BookDetailResponse;
import com.book_reading.dto.response.BookHotResponse;
import com.book_reading.dto.response.BookResponse;
import com.book_reading.service.BookService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    public ApiResponse<BookResponse> createBook(@RequestBody BookCreationRequest request){
        return ApiResponse.<BookResponse>builder()
                .message("Book creation successful")
                .data(bookService.createBook(request))
                .build();
    }

    @GetMapping("/{bookId}")
    public ApiResponse<BookDetailResponse> getBookById(@PathVariable String bookId) {
        return ApiResponse.<BookDetailResponse>builder()
                .message("Get Book detail successful")
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

    @GetMapping("/hot")
    public ApiResponse<List<BookHotResponse>> getHotBooks() {
        return ApiResponse.<List<BookHotResponse>>builder()
                .message("Get Hot Books Successful")
                .data(bookService.getHotBooks())
                .build();
    }

    @GetMapping("/hot/{genreId}")
    public ApiResponse<List<BookHotResponse>> getHotBooksByGenre(@PathVariable String genreId) {
        return ApiResponse.<List<BookHotResponse>>builder()
                .message("Get Hot Books By Genre Successful")
                .data(bookService.getHotBooksByGenre(genreId))
                .build();
    }

    @GetMapping("/{genreId}/books")
    public ApiResponse<List<BookResponse>> getAllBooksByGenre(@PathVariable String genreId) {
        return ApiResponse.<List<BookResponse>>builder()
                .message("Get all books by genre successful")
                .data(bookService.getAllBooksByGenre(genreId))
                .build();
    }

    @GetMapping("/favorite")
    public ApiResponse<Page<BookResponse>> getListBooksFavorite(@RequestParam String username,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);

        return ApiResponse.<Page<BookResponse>>builder()
                .message("Get all favorite books successful")
                .data(bookService.getListBooksFavorite(username, pageable))
                .build();
    }

    @GetMapping("/vip")
    public ApiResponse<List<BookResponse>> getAllVipBooks() {
        return ApiResponse.<List<BookResponse>>builder()
                .message("Get all VIP books successful")
                .data(bookService.getAllVipBooks())
                .build();
    }

    @GetMapping("/genre/{genreId}")
    public ApiResponse<Page<BookResponse>> getAllBooksByGenre(@PathVariable String genreId,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);

        return ApiResponse.<Page<BookResponse>>builder()
                .message("Get all books by genre successful")
                .data(bookService.getAllBooksByGenre(genreId, pageable))
                .build();
    }


    @GetMapping("/search")
    public ApiResponse<Page<BookResponse>> searchBooks(@RequestParam String keyword,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "15") int size){
        Pageable pageable = PageRequest.of(page, size);

        return ApiResponse.<Page<BookResponse>>builder()
                .message("Search books successful")
                .data(bookService.searchBooks(keyword, pageable))
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

    @PostMapping("/{bookId}/view")
    public ResponseEntity<Void> incrementView(@PathVariable String bookId) {
        bookService.incrementTotalView(bookId);
        return ResponseEntity.ok().build();
    }
}
