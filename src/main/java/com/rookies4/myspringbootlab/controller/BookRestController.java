package com.rookies4.myspringbootlab.controller;

import com.rookies4.myspringbootlab.entity.Book;
import com.rookies4.myspringbootlab.exception.BusinessException;
import com.rookies4.myspringbootlab.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books-rest")
public class BookRestController {
    private final BookRepository bookRepository;


    //POST 도서 등록
    @PostMapping
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    //GET 모든 도서 조회
    @GetMapping
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    //GET ID로 특정 도서 조회
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id){
        Book existBook = getExistId(id);
        return existBook;
    }

    //GET ISBN으로 도서 조회
    @GetMapping("/isbn/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn) {
        Book existBook = getExistIsbn(isbn);
        return existBook;
    }

    //PUT 도서 정보 수정
    @PatchMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book bookDetail){
        Book existBook = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("도서가 존재하지 않습니다.", HttpStatus.NOT_FOUND));
        existBook.setTitle(bookDetail.getTitle());
        existBook.setAuthor((bookDetail.getAuthor()));
        existBook.setIsbn((bookDetail.getIsbn()));
        existBook.setPrice(bookDetail.getPrice());
        existBook.setPublishDate(bookDetail.getPublishDate());

        Book updateBook = bookRepository.save(existBook);
        return updateBook;
    }

    //DELETE 도서 삭제nd
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        Book existBook = getExistId(id);

        bookRepository.delete(existBook);
        return ResponseEntity.ok("도서가 삭제되었습니다.");
    }



    private Book getExistId(Long id){
        Optional<Book> optionalBook = bookRepository.findById(id);

        Book existBook = optionalBook
                .orElseThrow(() -> new BusinessException("도서가 존재하지 않습니다."));
        return existBook;
    }


    private Book getExistIsbn(String isbn){
        Optional<Book> optionalBook = bookRepository.findByIsbn(isbn);

        Book existBook = optionalBook
                .orElseThrow(() -> new BusinessException("도서가 존재하지 않습니다."));

        return existBook;
    }
}
