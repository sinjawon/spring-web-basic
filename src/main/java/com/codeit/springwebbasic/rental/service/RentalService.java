package com.codeit.springwebbasic.rental.service;

import com.codeit.springwebbasic.book.entity.Book;
import com.codeit.springwebbasic.book.repository.BookRepository;
import com.codeit.springwebbasic.member.entity.Member;
import com.codeit.springwebbasic.member.repository.MemberRepository;
import com.codeit.springwebbasic.notification.NotificationService;
import com.codeit.springwebbasic.rental.entity.Rental;
import com.codeit.springwebbasic.rental.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RentalService {

    //이넘은 죄에에에다 의존하고 있다
    private final RentalRepository rentalRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    //무엇이 올지 몰라서
    private final NotificationService notificationService;//@priamry console주입

    public Rental rentBook(Long memberId, Long bookId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을수강벗다"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("도서를 찾을수가 없다"));

        book.rentOut();


        Rental rental = Rental.builder()
                .member(member)
                .book(book)
                .rentedAt(LocalDateTime.now())
                .dueDate(LocalDateTime.now().plusDays(7))
                .build();

       Rental saved = rentalRepository.save(rental);

       //알림발송
        //printf식으로 s넣어
        String message =
                String
                        .format("%s님 ,'%s' 도서를 대여했다 반납기한 : %s"
                                ,member.getName()
                                ,book.getTitle()
                                ,rental.getDueDate().toLocalDate()
                        );
        notificationService.sendNotification(member.getName(),message);
        return rental;
    }
}
