package com.codeit.springwebbasic.book.common.exception;

import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {
    //컨트롤러 단에서 모든 예외를 일괄적으로 처리 하는 클래스
    //실제 예외는 servic계층에 발생하지만 따로 예외처리가 없는경우
    //메서드를 호출한 상위 계층으로 잔파된다

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgsHandler(IllegalArgumentException e){
        e.printStackTrace(); //어디서발생했고 어디서했는지

        //에외의 원일을 http 상태 코드와 메세지를 통해 알려주고 싶다
        //RsponseEntity
        //품고있는것이 스트링
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

    }
    //미처 준비하지 못한 타입의 예외가 있을수가 있다
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
