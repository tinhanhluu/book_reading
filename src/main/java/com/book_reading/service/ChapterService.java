package com.book_reading.service;

import com.book_reading.dto.request.ChapterCreationRequest;
import com.book_reading.dto.response.ChapterResponse;
import com.book_reading.entity.Book;
import com.book_reading.entity.Chapter;
import com.book_reading.exception.AppException;
import com.book_reading.exception.ErrorCode;
import com.book_reading.mapper.ChapterMapper;
import com.book_reading.repository.BookRepository;
import com.book_reading.repository.ChapterRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChapterService {
    ChapterRepository chapterRepository;
    BookRepository bookRepository;
    ChapterMapper chapterMapper;

    public ChapterResponse createChapter(ChapterCreationRequest request) {
        var book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        Chapter chapter = Chapter.builder()
                .book(book)
                .chapterNumber(request.getChapterNumber())
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(LocalDate.now())
                .build();
        return chapterMapper.toChapterResponse(chapterRepository.save(chapter));
    }

    public List<ChapterResponse> getAllChapters(String bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        return chapterMapper.toAllChapterResponses(chapterRepository.findAllByBook(book));
    }

    public ChapterResponse getChapter(String bookId, int chapterNumber) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        Chapter chapter = chapterRepository.findByBookAndChapterNumber(book, chapterNumber)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        return  chapterMapper.toChapterResponse(chapter);
    }

}
