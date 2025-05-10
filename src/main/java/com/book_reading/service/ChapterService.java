package com.book_reading.service;

import com.book_reading.dto.request.ChapterCreationRequest;
import com.book_reading.dto.response.ChapterResponse;
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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChapterService {
    ChapterRepository chapterRepository;
    BookRepository bookRepository;
    private final ChapterMapper chapterMapper;

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



}
