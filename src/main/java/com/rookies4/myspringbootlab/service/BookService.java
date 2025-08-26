package com.rookies4.myspringbootlab.service;

import com.rookies4.myspringbootlab.dto.BookDTO;
import com.rookies4.myspringbootlab.entity.Book;
import com.rookies4.myspringbootlab.entity.BookDetail;
import com.rookies4.myspringbootlab.exception.BusinessException;
import com.rookies4.myspringbootlab.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<BookDTO.Response> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }

    public BookDTO.Response getBookById(Long id) {
        Book book = bookRepository.findByIdWithBookDetail(id)
                .orElseThrow(() -> new BusinessException("책을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        return BookDTO.Response.fromEntity(book);
    }

    public BookDTO.Response getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbnWithBookDetail(isbn)
                .orElseThrow(() -> new BusinessException("책을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        return BookDTO.Response.fromEntity(book);
    }

    public List<BookDTO.Response> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author).stream()
                .map(BookDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }

    public List<BookDTO.Response> getBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(BookDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }

    public BookDTO.Response createBook(BookDTO.Request request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException("이미 존재하는 ISBN입니다.", HttpStatus.CONFLICT);
        }

        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .publishDate(request.getPublishDate())
                .build();

        if (request.getDetailRequest() != null) {
            BookDetail detail = BookDetail.builder()
                    .description(request.getDetailRequest().getDescription())
                    .language(request.getDetailRequest().getLanguage())
                    .pageCount(request.getDetailRequest().getPageCount())
                    .publisher(request.getDetailRequest().getPublisher())
                    .coverImageUrl(request.getDetailRequest().getCoverImageUrl())
                    .edition(request.getDetailRequest().getEdition())
                    .build();
            book.setBookDetail(detail);
        }

        Book saved = bookRepository.save(book);
        return BookDTO.Response.fromEntity(saved);
    }

    public BookDTO.Response updateBook(Long id, BookDTO.Request request) {
        Book book = bookRepository.findByIdWithBookDetail(id)
                .orElseThrow(() -> new BusinessException("책을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPrice(request.getPrice());
        book.setPublishDate(request.getPublishDate());

        if (book.getBookDetail() != null && request.getDetailRequest() != null) {
            BookDetail detail = book.getBookDetail();
            detail.setDescription(request.getDetailRequest().getDescription());
            detail.setLanguage(request.getDetailRequest().getLanguage());
            detail.setPageCount(request.getDetailRequest().getPageCount());
            detail.setPublisher(request.getDetailRequest().getPublisher());
            detail.setCoverImageUrl(request.getDetailRequest().getCoverImageUrl());
            detail.setEdition(request.getDetailRequest().getEdition());
        }

        return BookDTO.Response.fromEntity(book);
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("책을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        bookRepository.delete(book);
    }
}