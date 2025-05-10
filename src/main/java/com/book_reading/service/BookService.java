package com.book_reading.service;

import com.book_reading.dto.request.BookCreationRequest;
import com.book_reading.dto.request.BookUpdateRequest;
import com.book_reading.dto.response.BookResponse;
import com.book_reading.entity.Book;
import com.book_reading.exception.AppException;
import com.book_reading.exception.ErrorCode;
import com.book_reading.mapper.BookMapper;
import com.book_reading.repository.AuthorRepository;
import com.book_reading.repository.BookRepository;
import com.book_reading.repository.GenreRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookService {
    BookRepository bookRepository;
    AuthorRepository authorRepository;
    GenreRepository genreRepository;

    BookMapper bookMapper;


    public BookResponse createBook(BookCreationRequest request){

        var author = authorRepository.findByName(request.getAuthor())
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));
        var genre = genreRepository.findByName(request.getGenre())
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));

        Book book = Book.builder()
                .title(request.getTitle())
                .author(author)
                .genre(genre)
                .releaseDate(request.getReleaseDate())
                .description(request.getDescription())
                .build();

        return bookMapper.toBookResponse(bookRepository.save(book));
    }

    public BookResponse updateBook(String bookId, BookUpdateRequest request){
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        bookMapper.toUpdateBook(request, book);

        return bookMapper.toBookResponse(bookRepository.save(book));
    }

    public BookResponse getBookById(String bookId){
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        return bookMapper.toBookResponse(book);
    }

    public List<BookResponse> getAllBooks(){
        return bookMapper.toListBookResponse(bookRepository.findAll());
    }

    public List<BookResponse> getAllBooksByGenre(String genreId){
        var genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        return bookMapper.toListBookResponse(bookRepository.findByGenre(genre));
    }

    public List<BookResponse> getAllBooksByAuthor(String authorId){
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        return bookMapper.toListBookResponse(bookRepository.findByAuthor(author));
    }

    public void deleteBook(String bookId){
        var book = bookRepository.findById(bookId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        bookRepository.delete(book);
    }

}
