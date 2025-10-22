package com.codeit.springwebbasic.ntification;

import com.codeit.springwebbasic.notification.NotificationService;
import com.codeit.springwebbasic.rental.entity.Rental;
import org.springframework.stereotype.Service;

import java.util.List;

//디스페쳐의미가 받아서 전담을 해주는 것을
@Service//컨포넌트도 된다
public class NotificationDispatcher {

    //컬랙션 주입받기
   private  final List<NotificationService> allServices;


    public NotificationDispatcher(List<NotificationService> allServices) {
        this.allServices = allServices;
        System.out.println("등록된 알림 서비스 " + allServices.size() + "개");
    }

    public void broadcast(String recipient , String message){
        allServices.forEach(service -> {
            service.sendNotification(recipient, message);
        });
    }
}
