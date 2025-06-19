package com.book_reading.repository;

import com.book_reading.entity.UserBookProgress;
import com.book_reading.entity.UserBookProgressId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBookProgressRepository extends JpaRepository<UserBookProgress, UserBookProgressId> {
}
