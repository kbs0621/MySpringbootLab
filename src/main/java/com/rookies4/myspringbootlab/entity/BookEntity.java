package com.rookies4.myspringbootlab.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Table(name="books")
@Getter @Setter
@DynamicUpdate
public class BookEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    private String isbn;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime publishDate = LocalDateTime.now();

    private int price;



}
