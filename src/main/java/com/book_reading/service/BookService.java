package com.book_reading.service;

import com.book_reading.dto.request.BookCreationRequest;
import com.book_reading.dto.request.BookUpdateRequest;
import com.book_reading.dto.response.BookDetailResponse;
import com.book_reading.dto.response.BookHotResponse;
import com.book_reading.dto.response.BookResponse;
import com.book_reading.entity.Book;
import com.book_reading.entity.UserBookFavorite;
import com.book_reading.entity.UserBookFavoriteId;
import com.book_reading.exception.AppException;
import com.book_reading.exception.ErrorCode;
import com.book_reading.mapper.BookMapper;
import com.book_reading.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookService {
    BookRepository bookRepository;
    AuthorRepository authorRepository;
    GenreRepository genreRepository;
    UserRepository userRepository;
    UserBookFavoriteRepository userBookFavoriteRepository;

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

    public BookDetailResponse getBookById(String bookId){
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        return bookMapper.toBookDetailResponse(book);
    }

    public Page<BookResponse> getAllBooksByGenre(String genreId, Pageable pageable){
        var genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));

        Page<Book> pageBook = bookRepository.findByGenre(genre, pageable);

        return bookMapper.toPageBookResponse(pageBook);

    }

    public List<BookResponse> getAllBooks(){
        return bookMapper.toListBookResponse(bookRepository.findAll());
    }

    public List<BookHotResponse> getHotBooks(){
        return bookMapper.toListBookHotResponse(bookRepository.findTop7ByOrderByReleaseDateDesc());
    }

    public List<BookHotResponse> getHotBooksByGenre(String genreId){
        var genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));

        return bookMapper.toListBookByGenreResponse(bookRepository.findTop7ByGenreOrderByReleaseDateDesc(genre));
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

    public Page<BookResponse> getListBooksFavorite(String username, Pageable pageable){
        var user = userRepository.findByName(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));

        String userId = user.getId();

        // Lấy toàn bộ bản ghi yêu thích từ bảng trung gian
        Page<UserBookFavorite> favorites = userBookFavoriteRepository.findByIdUserId(userId, pageable);

        // Lấy danh sách Book từ các bản ghi UserBookFavorite
        return favorites.map(fav -> bookMapper.toBookResponse(fav.getBook()));
    }

    public void deleteBook(String bookId){
        var book = bookRepository.findById(bookId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        bookRepository.delete(book);
    }

    public Page<BookResponse> searchBooks(String keyword, Pageable pageable){
        return bookMapper.toPageBookResponse(bookRepository.findByTitleContainingIgnoreCase(keyword, pageable));
    }

    public List<BookResponse> getAllVipBooks(){
        return bookMapper.toListBookResponse(bookRepository.findByIsVipTrue());
    }

    public void incrementTotalView(String bookId){
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        book.setTotalView(book.getTotalView() + 1);
        bookRepository.save(book);
    }

}
