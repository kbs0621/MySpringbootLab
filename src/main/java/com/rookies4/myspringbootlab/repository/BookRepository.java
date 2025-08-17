package com.rookies4.myspringbootlab.repository;

import com.rookies4.myspringbootlab.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    Optional<BookEntity> findByIsbn(String isbn);
    Optional<BookEntity> findByAuthor(String author);
}
