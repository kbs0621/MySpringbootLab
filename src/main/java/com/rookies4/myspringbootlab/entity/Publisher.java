package com.rookies4.myspringbootlab.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publisher")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalDate establishedDate;

    @Builder.Default
    @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();


    // Helper methods
    public void addBook(Book book) {
        books.add(book);
        book.setPublisher(this);
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.setPublisher(null);
    }

}
