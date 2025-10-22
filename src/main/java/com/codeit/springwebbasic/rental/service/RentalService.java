package com.codeit.springwebbasic.rental.service;

import com.codeit.springwebbasic.book.entity.Book;
import com.codeit.springwebbasic.book.repository.BookRepository;
import com.codeit.springwebbasic.event.BookRentedEvent;
import com.codeit.springwebbasic.member.entity.Member;
import com.codeit.springwebbasic.member.repository.MemberRepository;
import com.codeit.springwebbasic.notification.NotificationService;
import com.codeit.springwebbasic.ntification.NotificationDispatcher;
import com.codeit.springwebbasic.rental.entity.Rental;
import com.codeit.springwebbasic.rental.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
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
   // private final NotificationService notificationService;//@priamry console주입
  //  private final NotificationDispatcher notificationDispatcher;

    //스프링 객체로 주입받겠다
    private final ApplicationEventPublisher eventPublisher;

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

       //대여완료 되었다는 이벤트 발행
        //대여완료되었다는 이벤트 발행 해당 이벤트에 맞는 객체를 생성해서 전달
        //이벤트 리스너 중 해당 객체를 매객ㅏㅄ을 ㅗ받을 수 있는 핸들러가 이벤트를 감지하고 로직을 수행
        eventPublisher.publishEvent(new BookRentedEvent(this, saved));


        //
       //알림발송  랜탈만한다 알림을 전담하는것을 주석처리하겠다
        //printf식으로 s넣어

    //    notificationService.sendNotification(member.getName(),message);

    //    notificationDispatcher.broadcast(member.getName(), message);


        return rental;
    }
}
