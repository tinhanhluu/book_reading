package com.book_reading.dto.response;

import com.book_reading.entity.Book;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AllChapterResponse {
    String id;
    Book book;
    String title;
    int chapterNumber;
    LocalDate createdAt;
}
