package com.codeit.springwebbasic.event.listener;

import com.codeit.springwebbasic.event.BookRentedEvent;
import com.codeit.springwebbasic.ntification.NotificationDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {


    private final NotificationDispatcher dispatcher;

    //이밴트 리스너가 있다
    //이벤트가 발생되면 해당 메서드가 자동을 ㅗ호출됩니다
    //매개값으로 이벤트 발행시 생성한 객체가 전달됩니다
    @EventListener
    public void handleBookRentedEvent(BookRentedEvent event) {

        String message = String
                        .format("%s님 ,'%s' 도서를 대여했다 반납기한 : %s"
                                ,event.getMember().getName()
                                ,event.getBook().getTitle()
                                ,event.getRenatl().getDueDate().toLocalDate()
                        );

        dispatcher.broadcast(event.getMember().getName(),message);
    }
}
