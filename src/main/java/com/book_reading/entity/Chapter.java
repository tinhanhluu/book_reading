package com.book_reading.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "bookId", nullable = false)
    Book book;

    String title;
    @Column(name = "content", columnDefinition = "MEDIUMTEXT")
    String content;
    int chapterNumber;
    LocalDate createdAt;

}
