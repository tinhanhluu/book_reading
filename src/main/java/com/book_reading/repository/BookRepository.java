package com.book_reading.repository;

import com.book_reading.entity.Author;
import com.book_reading.entity.Book;
import com.book_reading.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByGenre(Genre genre);
    List<Book> findByAuthor(Author author);

    void delete(Book book);
}
