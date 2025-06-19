package com.book_reading.service;

import com.book_reading.entity.Book;
import com.book_reading.entity.User;
import com.book_reading.entity.UserBookFavorite;
import com.book_reading.entity.UserBookFavoriteId;
import com.book_reading.exception.AppException;
import com.book_reading.exception.ErrorCode;
import com.book_reading.repository.BookRepository;
import com.book_reading.repository.UserBookFavoriteRepository;
import com.book_reading.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserBookFavoriteService {
    UserRepository userRepository;
    BookRepository bookRepository;
    UserBookFavoriteRepository userBookFavoriteRepository;

    @Transactional
    public boolean toggleFavorite(String username, String bookId) {
        User user = userRepository.findByName(username).orElse(null);
        Book book = bookRepository.findById(bookId).orElse(null);

        Optional<UserBookFavorite> existing = userBookFavoriteRepository.findByUserAndBook(user, book);
        UserBookFavoriteId id = new UserBookFavoriteId(book.getId(), user.getId());
        if (existing.isPresent()) {
            userBookFavoriteRepository.deleteByUserAndBook(user, book);
            book.setTotalLike(book.getTotalLike() - 1);
            return false; // Bỏ yêu thích
        } else {
            UserBookFavorite fav = new UserBookFavorite();
            fav.setId(id);
            fav.setUser(user);
            fav.setBook(book);
            book.setTotalLike(book.getTotalLike() + 1);
            userBookFavoriteRepository.save(fav);
            return true; // Đã yêu thích
        }
    }

    public boolean isFavorite(String username, String bookId) {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));
        Book book = bookRepository.findById(bookId).orElse(null);

        Optional<UserBookFavorite> existing = userBookFavoriteRepository.findByUserAndBook(user, book);
        if (existing.isPresent()) {
            return true; // Trạng thái đã yêu thích
        } else {
            return false; // Trạng thái chưa yêu thích
        }
    }
}
