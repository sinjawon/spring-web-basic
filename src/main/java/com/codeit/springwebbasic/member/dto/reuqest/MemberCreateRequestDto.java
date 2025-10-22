package com.codeit.springwebbasic.member.dto.reuqest;

import com.codeit.springwebbasic.member.MemberGrade;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;


@Getter

//혹시모를 상황대비해서 기본생성자
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreateRequestDto {


    @NotBlank(message = "이름 필수입니다")
    private String name;
    @NotBlank(message = "메세지는 필수입니다")
    @Email(message = "올바른 이메일 형식이 아니다")//메일형식인지 검사해준다
    private String email;
    @NotBlank(message = "전번도 필수다")
    @Pattern(regexp ="^01[016789]-\\d{3,4}-\\d{4}$",message = "전화번호 형식 인지해")
    private String phone;



}
