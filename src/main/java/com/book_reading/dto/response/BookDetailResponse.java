package com.book_reading.dto.response;

import com.book_reading.entity.Author;
import com.book_reading.entity.Genre;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDetailResponse {
    String id;
    String title;
    String description;
    String coverUrl;
    Author author;
    Genre genre;
    int totalView;
    int totalLike;
}
