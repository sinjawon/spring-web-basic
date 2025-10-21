package com.codeit.springwebbasic.book.entity;

import lombok.*;

import java.time.LocalDate;



@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class Book {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private LocalDate publishDate;
    //3가지로 만들고 싶다
    private  BookStatus status;


    //나중에 db를 사용하면 status값도db에 저장 돌 것이기 때문에
    //밑에 2개의 메서드는 사라지게 될겁니다
    public void rentOut(){
        if(status != BookStatus.AVAILABLE){
            throw new IllegalArgumentException("이미 대여중인 도서다");
        }
        this.status = BookStatus.RENTED;
    }
    public void returnBook(){
        if(status != BookStatus.RENTED){
            throw new IllegalArgumentException("대여중이 아닌 도서다");
        }
        this.status = BookStatus.AVAILABLE;
    }


}
