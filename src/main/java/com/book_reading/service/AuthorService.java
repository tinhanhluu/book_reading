package com.book_reading.service;

import com.book_reading.dto.request.AuthorCreationRequest;
import com.book_reading.dto.response.AuthorCreationResponse;
import com.book_reading.entity.Author;
import com.book_reading.exception.AppException;
import com.book_reading.exception.ErrorCode;
import com.book_reading.mapper.AuthorMapper;
import com.book_reading.repository.AuthorRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorService {
    AuthorRepository authorRepository;
    AuthorMapper authorMapper;

    public AuthorCreationResponse createAuthor(AuthorCreationRequest request){
        var author = authorRepository.findByName(request.getName()).orElse(null);

        if(author != null && authorRepository.existsById(author.getId())){
            throw new AppException(ErrorCode.EXISTED);
        }

        Author author1 = Author.builder()
                .name(request.getName())
                .build();

        return authorMapper.toAuthorResponse(authorRepository.save(author1));
    }

    public AuthorCreationResponse getAuthorById(String authorId){
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));

        return authorMapper.toAuthorResponse(author);
    }

    public List<AuthorCreationResponse> getAllAuthors(){
        return authorMapper.toAllAuthorsResponse(authorRepository.findAll());
    }
}
