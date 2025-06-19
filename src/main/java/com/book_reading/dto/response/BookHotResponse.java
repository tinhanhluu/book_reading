package com.book_reading.dto.response;

import com.book_reading.entity.Author;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookHotResponse {
    String id;
    String title;
    Author author;
    String coverUrl;

}
