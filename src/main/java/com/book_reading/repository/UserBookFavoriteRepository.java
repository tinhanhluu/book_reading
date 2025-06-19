package com.book_reading.repository;

import com.book_reading.entity.Book;
import com.book_reading.entity.User;
import com.book_reading.entity.UserBookFavorite;
import com.book_reading.entity.UserBookFavoriteId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBookFavoriteRepository extends JpaRepository<UserBookFavorite, UserBookFavoriteId> {
    Optional<UserBookFavorite> findByUserAndBook(User user, Book book);
    void deleteByUserAndBook(User user, Book book);

    Page<UserBookFavorite> findByIdUserId(String userId, Pageable pageable);
}
