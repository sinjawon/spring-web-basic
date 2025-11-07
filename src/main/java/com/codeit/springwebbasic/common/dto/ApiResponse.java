package com.codeit.springwebbasic.common.dto;

//이제부터 모든 api의 응답은 이 형식을 따를것이다

import lombok.Getter;

@Getter
public class ApiResponse <T> {

    //1공통 비즈니스상태 코드

    //sucess , user_not_fond,invatlid _input -> enum을 활용해도 상관ㅇ벗다
    private final String code;
    //2. 비즈니스 상태 메시지 (삭태 코드보다 좀 더 상세한 결과 보고 )
    private final String message;

    private final T data;


    //모든 값을 다 받는 생성자
    //정적 팩토리 메서드 사용 강제
    private ApiResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    //정적 팩토리 매서드 static factory method
    //객체를 생성할 때 생성자 호출이 아닌 static메서드로 간접적으로 생성자 호출
    //심플하게
    //1.성공 응답 (데이터 포함)
    public static <T> ApiResponse<T> success(T data){
        return new ApiResponse<>("SUCESS","요청에 성공했습니다",data);
    }
    //2.성공 응답(데이터 없음)
    //null을 넣읋수도 있짐나 명싲거으로 넣을경우도있다
    public static <T> ApiResponse<T> err(String code, String message, T data){
        return new ApiResponse<>(code,message,null);
    }
    //3. 실패응답 (커스텀 코드와 메시지 )


}
