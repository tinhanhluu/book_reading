package com.book_reading.mapper;

import com.book_reading.entity.Author;
import com.book_reading.exception.AppException;
import com.book_reading.exception.ErrorCode;
import com.book_reading.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapperHelper {
    @Autowired
    private AuthorRepository authorRepository;

    public Author map(String authorId){
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));
    }
}
