package com.book_reading.repository;

import com.book_reading.entity.Author;
import com.book_reading.entity.Book;
import com.book_reading.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByIsVipTrue();
    List<Book> findByGenre(Genre genre);
    List<Book> findByAuthor(Author author);
    List<Book> findTop7ByOrderByReleaseDateDesc();

    List<Book> findTop7ByGenreOrderByReleaseDateDesc(Genre genre);

    Page<Book> findByGenre(Genre genre, Pageable pageable);

    void delete(Book book);

    Page<Book> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

}
