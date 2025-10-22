package com.codeit.springwebbasic.rental.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RentalRequestDto {

    @NotNull(message = "회원 id 는 필수더ㅏ")//문자열이 가능하다
    private  Long memberId;
    @NotNull(message = "도서 id 는 필수더ㅏ")
    private  Long bookId;
}
