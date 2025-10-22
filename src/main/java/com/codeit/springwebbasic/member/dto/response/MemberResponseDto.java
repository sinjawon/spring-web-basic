package com.codeit.springwebbasic.member.dto.response;

import com.codeit.springwebbasic.member.MemberGrade;
import com.codeit.springwebbasic.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;



@Getter
@Builder
public class MemberResponseDto {

    //엔터티를 직접 리턴하지 않고
    //응답용 dto를 생성해서 리턴

    private Long id;
    private String name;
    private String email;
    private String phone;
    private MemberGrade grade;
    private LocalDateTime joinedAt;


    public static MemberResponseDto from(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .grade(member.getGrade())
                .joinedAt(member.getJoinedAt())
                .build();
    }
}
