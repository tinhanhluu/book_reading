package com.book_reading.entity;

import com.book_reading.enums.Roles;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String email;
    String password;
    String name;
    String avatarUrl;
    Date createdAt;
    LocalDate birthday;

    @Enumerated(EnumType.STRING)
    Roles role;

    String provider;
    String providerId;

    boolean isVip = false;
    LocalDate vipExpiryDate;
}
