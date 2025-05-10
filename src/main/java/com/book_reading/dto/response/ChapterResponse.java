package com.book_reading.dto.response;

import com.book_reading.entity.Book;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterResponse {
    String id;

    Book book;

    String title;
    String content;
    int chapterNumber;
    LocalDate createdAt;
}
