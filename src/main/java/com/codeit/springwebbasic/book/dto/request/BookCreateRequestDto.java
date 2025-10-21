package com.codeit.springwebbasic.book.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

//dto는 데이터 트렌스퍼 오브잭트
//비즈니스 로직 없이 순수하게 전달하고 싶은 데이터를 담는 용도로 사용하는 객체
//일반적으로 요청 , 응답으로 나누고 ,꼭 필요한 데이텀나 정제해서 전달하는 용도로 사용
//entity직접 사용하지 않는 이유
//1.필요없는 정보까지 노출 될 우험
//2.도메인이 바뀌면 다른 스펙도 전부 변경 ,3. 검증 로직 배치의 애매함

@Getter  @ToString
//혹시모를 상황대비해서 기본생성자
@AllArgsConstructor
@NoArgsConstructor
//Valled 붙이면 검증이 가능해진다
public class BookCreateRequestDto {

    @NotBlank(message = "제목은 필수입니다")
  //  @siz(min=2,max = 10)
    private String title;

    @NotBlank(message = "저자는 필수입니다")
    private String author;
    @NotBlank(message = "ISBN은 필수입니다")
    //정규표현식 문자열의 일정한 패턴을 표현하는 형식 언어
    @Pattern(regexp ="\\d{13}",message = "ISBN은 13자리 숫자영야한다")
    private String isbn;
    @NotBlank(message = "출판사가 없겠냐")
    private String publisher;
    @PastOrPresent(message = "출판일은 과거 또는 현재이여야 한다.")
    private LocalDate publishedDate;
}
