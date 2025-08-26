package com.rookies4.myspringbootlab.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDetail {

    //PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_detail_id")
    private Long id;

    //내용
    @Column(nullable = false)
    private String description;

    //언어
    @Column(nullable = false)
    private String language;

    //페이지 수
    @Column(nullable = false)
    private int pageCount;

    //출판사
    @Column(nullable = false)
    private String publisher;

    //표지 이미지 URL
    @Column(nullable = false)
    private String coverImageUrl;

    //에디션
    @Column(nullable = false)
    private String edition;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", unique = true)
    private Book book;

}
