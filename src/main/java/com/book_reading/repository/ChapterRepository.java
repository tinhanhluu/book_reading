package com.book_reading.repository;

import com.book_reading.dto.response.ChapterResponse;
import com.book_reading.entity.Book;
import com.book_reading.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, String> {

    List<Chapter> findAllByBook(Book book);
    Optional<Chapter> findByBookAndChapterNumber(Book book, int chapterNumber);
}
