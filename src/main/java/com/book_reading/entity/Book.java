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
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "authorId")
    Author author;

    @ManyToOne
    @JoinColumn(name = "genreId")
    Genre genre;

    String title;
    @Column(name = "description", columnDefinition = "MEDIUMTEXT")
    String description;
    LocalDate releaseDate;
    String coverUrl;
    int totalView;
    int totalLike;
    boolean isVip  = false;
}
