package com.codeit.springwebbasic.book.dto.response;

import com.codeit.springwebbasic.book.entity.Book;
import com.codeit.springwebbasic.book.entity.BookStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
@Getter
@Builder
public class BookResponseDto {

    //실습이라 boo과 응답 dto차이가 없지만 실질적으로 는
    //화면 client단에 맞는 데이터만 정제해서 보내는것이 일반적
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private LocalDate publishDate;
    private BookStatus status;

    //book 객체를 응답용 DTO로 변환해 주는 유틸 메서드
    public static BookResponseDto from(Book book){
        return  BookResponseDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publisher(book.getPublisher())
                .publishDate(book.getPublishDate())
                .status(book.getStatus())
                .build();
    }

}
