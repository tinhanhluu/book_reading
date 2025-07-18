package com.book_reading.service;

import com.book_reading.repository.UserBookProgressRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserBookProgressService {

    UserBookProgressRepository userBookProgressRepository;

}
