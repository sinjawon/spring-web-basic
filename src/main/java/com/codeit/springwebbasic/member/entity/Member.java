package com.codeit.springwebbasic.member.entity;

import com.codeit.springwebbasic.member.MemberGrade;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private MemberGrade grade;
    private LocalDateTime joinedAt;

    //등급 업그레이드
    //기존의 등급에서 한 단계 업그레이드
    public void upgradeGrade(){
        this.grade =this.grade.upgrade();
    }
}
