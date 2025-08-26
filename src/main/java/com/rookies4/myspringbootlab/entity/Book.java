package com.rookies4.myspringbootlab.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="books")
@Data
@Getter @Setter
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, unique = true)
    private String isbn;


    @Column(nullable = false)
    private int price;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDate publishDate;


    @OneToOne(fetch = FetchType.LAZY,
            mappedBy = "book",
            cascade = CascadeType.ALL)

    private BookDetail bookDetail;

}
