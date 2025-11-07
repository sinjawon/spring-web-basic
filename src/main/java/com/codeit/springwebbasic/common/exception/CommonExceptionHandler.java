package com.codeit.springwebbasic.common.exception;

import com.codeit.springwebbasic.common.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {
    //컨트롤러 단에서 모든 예외를 일괄적으로 처리 하는 클래스
    //실제 예외는 servic계층에 발생하지만 따로 예외처리가 없는경우
    //메서드를 호출한 상위 계층으로 잔파된다

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> illegalArgsHandler(IllegalArgumentException e){
        //log.error사용해서 예외 객체 전달하면 스택 트레이스를 모두 찍어줍니다
        log.error(e.getMessage(),e);
     //   e.printStackTrace(); //어디서발생했고 어디서했는지
     //
        ApiResponse<Object> response= ApiResponse.err("INVALID_ARGS",e.getMessage(),null);

        //에외의 원일을 http 상태 코드와 메세지를 통해 알려주고 싶다
        //RsponseEntity
        //품고있는것이 스트링
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidHandler(MethodArgumentNotValidException e){
        e.printStackTrace();

        //가공이 필요하다
        //1. 오류 결과를 담을 map을 생성합니다 (KEY: 필드명 VALUE: 에러 메세지)
        Map<String,String> errors= new HashMap<>();

        BindingResult bindingResult = e.getBindingResult();

        //짧게 표현하려고
        //갯바인딩 BindingResult: 리절트 오류 결과 보고서
        //@vaidl실패한 필드 목록
    /*    List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        for (FieldError error : fieldErrors) {
            String field = error.getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        }*/
        e.getBindingResult().getFieldErrors().forEach(error->{
                String field = error.getField();
             String message = error.getDefaultMessage();
        errors.put(field, message);
        });

        return  ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }



    //미처 준비하지 못한 타입의 예외가 있을수가 있다
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
