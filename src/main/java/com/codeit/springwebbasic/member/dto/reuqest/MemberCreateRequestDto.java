package com.codeit.springwebbasic.member.dto.reuqest;

import com.codeit.springwebbasic.member.MemberGrade;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;
//Getter ,@NoArgsConstructor, @AllArgsConstructor
// 자바 16부터 사용된 객체 불변 데이터 운반 객체를 아ㅏ주 간결하게 작성할수 있도록 제공되는 클래스
//특히 데이터 운반하려고하는 데이터 dto로 보통 롬복으로 줄였지만
//컴파일과정에서 Getter ,@NoArgsConstructor, @AllArgsConstructor @Totring 생성해준다
//record를 불변의 객체로 만들어 준다 (필드에 private final이 다 붙는다) setter제공 안된다
//getter 기본재공이지만 이름에 get이 붙지 않는다
//제이슨데이더 변환도 완벽하게 된다
//롬복의 빌더 사용가능하다 목적에 맞게 사용해야한다
//리퀘스트는 빌더를 만들일이 없다
//응답 dto는 빌더가 들어가면 좀 편하다 서비스에서 직접 생성해야하니
public record MemberCreateRequestDto(

        @NotBlank(message = "이름 필수입니다")
        String name,

        @NotBlank(message = "메세지는 필수입니다")
        @Email(message = "올바른 이메일 형식이 아니다")//메일형식인지 검사해준다
        String email,

        @NotBlank(message = "전번도 필수다")
        @Pattern(regexp ="^01[016789]-\\d{3,4}-\\d{4}$",message = "전화번호 형식 인지해")
        String phone

){
  //본문이 비어있어도 됩니다
    //필요하다면 여기에 다른 생성자 정적 메서드 등을 선언할수가 있다
}









