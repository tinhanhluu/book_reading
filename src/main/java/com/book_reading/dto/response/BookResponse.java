package com.book_reading.dto.response;

import com.book_reading.entity.Author;
import com.book_reading.entity.Genre;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponse {
    String id;

    Author author;
    Genre genre;

    String title;
    String description;
    LocalDate releaseDate;
    String coverUrl;
    int totalView;
    int totalLike;
    boolean isVip  = false;
}
