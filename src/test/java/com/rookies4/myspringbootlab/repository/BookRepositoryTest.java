package com.rookies4.myspringbootlab.repository;

import com.rookies4.myspringbootlab.entity.BookEntity;
import com.rookies4.myspringbootlab.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    // ✅ 도서 등록 테스트
    @Test
    public void testCreateBook() {
        Book book = new Book("스프링 부트 입문", "홍길동", "9788956746425",
                LocalDate.now(), 30000);

        Book saved = bookRepository.save(book);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("스프링 부트 입문");
    }

    // ✅ ISBN으로 도서 조회 테스트
    @Test
    public void testFindByIsbn() {
        Book book = new Book("JPA 프로그래밍", "박둘리", "9788956746432",
                LocalDate.now(), 35000);
        bookRepository.save(book);

        Optional<Book> found = bookRepository.findByIsbn("9788956746432");

        assertThat(found).isPresent();
        assertThat(found.get().getAuthor()).isEqualTo("박둘리");
    }

    // ✅ 저자명으로 도서 목록 조회 테스트
    @Test
    public void testFindByAuthor() {
        bookRepository.save(new Book("스프링 부트 입문", "홍길동", "9788956746425",
                LocalDate.now(), 30000));
        bookRepository.save(new Book("JPA 프로그래밍", "박둘리", "9788956746432",
                LocalDate.now(), 35000));

        List<Book> books = bookRepository.findByAuthor("홍길동");

        assertThat(books).isNotEmpty();
        assertThat(books.get(0).getTitle()).isEqualTo("스프링 부트 입문");
    }

    // ✅ 도서 정보 수정 테스트
    @Test
    public void testUpdateBook() {
        Book book = new Book("스프링 부트 입문", "홍길동", "9788956746425",
                LocalDate.now(), 30000);
        Book saved = bookRepository.save(book);

        // 가격 변경
        saved.setPrice(32000);
        Book updated = bookRepository.save(saved);

        assertThat(updated.getPrice()).isEqualTo(32000);
    }

    // ✅ 도서 삭제 테스트
    @Test
    public void testDeleteBook() {
        Book book = new Book("JPA 프로그래밍", "박둘리", "9788956746432",
                LocalDate.now(), 35000);
        Book saved = bookRepository.save(book);

        bookRepository.delete(saved);

        Optional<Book> found = bookRepository.findById(saved.getId());
        assertThat(found).isEmpty();
    }
}