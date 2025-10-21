package com.codeit.springwebbasic.rental.entity;

import com.codeit.springwebbasic.book.entity.Book;
import com.codeit.springwebbasic.member.entity.Member;

import java.time.LocalDateTime;

public class Rental {
    private Long id;
    private Member member;
    private Book book;
    private LocalDateTime rentedAt;
    private LocalDateTime dueDate;
    private LocalDateTime returnedAt;

    public void returnBook() {
        if (this.rentedAt != null) {
            throw new IllegalArgumentException("이미 반납된 도서다");
        }
        this.book.returnBook();//book의 status 변경
        this.returnedAt = LocalDateTime.now();//현재 날짜 ,시간 정보
    }

    //기한이 지났는지를 체크
    public boolean isOverdue() {
        //반납이 되었다면 반납 날짜로 체크 , 아직 반납이 안되었다면 현재 날짜로 체크
         LocalDateTime checkDate = returnedAt != null ? returnedAt : LocalDateTime.now();

         return checkDate.isAfter(this.dueDate);
    }
}
