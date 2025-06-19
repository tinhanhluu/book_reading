package com.book_reading.controller;

import com.book_reading.service.UserBookFavoriteService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/favorite")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserBookFavoriteController {
    UserBookFavoriteService userBookFavoriteService;

    @PostMapping("/toggle")
    public ResponseEntity<?> toggleFavorite(@RequestParam String username, @RequestParam String bookId) {
        boolean isFavorite = userBookFavoriteService.toggleFavorite(username, bookId);
        return ResponseEntity.ok(Map.of("isFavorite", isFavorite));
    }

    @GetMapping("/toggle")
    public ResponseEntity<?> isFavorite(@RequestParam String username, @RequestParam String bookId) {
        return ResponseEntity.ok(Map.of("isFavorite", userBookFavoriteService.isFavorite(username, bookId)));
    }
}
