package com.codeit.springwebbasic.event;

import com.codeit.springwebbasic.book.entity.Book;
import com.codeit.springwebbasic.member.entity.Member;
import com.codeit.springwebbasic.rental.entity.Rental;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

@Getter
public class BookRentedEvent extends ApplicationEvent {

    private final Rental renatl;
    private final Member member;
    private final Book book;

    public BookRentedEvent(Object source, Rental renatl) {
        super(source);
        this.renatl = renatl;
        this.member = renatl.getMember();
        this.book = renatl.getBook();
    }


}
