package com.rookies4.myspringbootlab.dto;

import com.rookies4.myspringbootlab.entity.Book;
import com.rookies4.myspringbootlab.entity.BookDetail;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

public class BookDTO {

    @Getter
    @Setter
    public static class Request {
        @NotBlank
        private String title;

        @NotBlank
        private String author;

        @Pattern(regexp = "^[0-9]{13}$", message = "ISBN은 13자리 숫자여야 합니다.")
        private String isbn;

        @PositiveOrZero
        private Integer price;

        @PastOrPresent
        private LocalDate publishDate;

        private BookDetailDTO detailRequest;
    }

    @Getter
    @Setter
    public static class BookDetailDTO {
        private String description;
        private String language;
        private Integer pageCount;
        private String publisher;
        private String coverImageUrl;
        private String edition;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;
        private BookDetailResponse detail;

        public static Response fromEntity(Book book) {
            return Response.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .isbn(book.getIsbn())
                    .price(book.getPrice())
                    .publishDate(book.getPublishDate())
                    .detail(book.getBookDetail() != null ? BookDetailResponse.fromEntity(book.getBookDetail()) : null)
                    .build();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BookDetailResponse {
        private Long id;
        private String description;
        private String language;
        private Integer pageCount;
        private String publisher;
        private String coverImageUrl;
        private String edition;

        public static BookDetailResponse fromEntity(BookDetail detail) {
            return BookDetailResponse.builder()
                    .id(detail.getId())
                    .description(detail.getDescription())
                    .language(detail.getLanguage())
                    .pageCount(detail.getPageCount())
                    .publisher(detail.getPublisher())
                    .coverImageUrl(detail.getCoverImageUrl())
                    .edition(detail.getEdition())
                    .build();
        }
    }
}