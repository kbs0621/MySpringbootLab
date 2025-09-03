package com.rookies4.myspringbootlab.repository;

import com.rookies4.myspringbootlab.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // ISBN으로 조회
    Optional<Book> findByIsbn(String isbn);

    // 저자 이름으로 조회
    List<Book> findByAuthor(String author);

    // 저자 이름 포함 검색 (대소문자 무시)
    List<Book> findByAuthorContainingIgnoreCase(String author);

    // 제목 포함 검색 (대소문자 무시)
    List<Book> findByTitleContainingIgnoreCase(String title);

    // BookDetail fetch
    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.bookDetail WHERE b.id = :id")
    Optional<Book> findByIdWithBookDetail(@Param("id") Long id);

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.bookDetail WHERE b.isbn = :isbn")
    Optional<Book> findByIsbnWithBookDetail(@Param("isbn") String isbn);

    // 존재 여부 확인
    boolean existsByIsbn(String isbn);

    // Publisher 관련 조회
    List<Book> findByPublisherId(Long publisherId);

    Long countByPublisherId(@Param("publisherId") Long publisherId);

    // 모든 연관 엔티티 fetch
    @Query("SELECT b FROM Book b " +
            "LEFT JOIN FETCH b.bookDetail " +
            "LEFT JOIN FETCH b.publisher " +
            "WHERE b.id = :id")
    Optional<Book> findByIdWithAllDetails(@Param("id") Long id);

}