package com.codeit.springwebbasic.member.service;

import com.codeit.springwebbasic.member.MemberGrade;
import com.codeit.springwebbasic.member.dto.response.MemberResponseDto;
import com.codeit.springwebbasic.member.dto.reuqest.MemberCreateRequestDto;
import com.codeit.springwebbasic.member.entity.Member;
import com.codeit.springwebbasic.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j //이것이  private final Logger log = LoggerFactory.getLogger(MemberService.class);
public class MemberService {

    private final MemberRepository memberRepository;
    //여기서 사용할꺼야
    //그치만 롬복을한다면?
  //  private final Logger log = LoggerFactory.getLogger(MemberService.class);


   public MemberResponseDto createMember(MemberCreateRequestDto requestDto){

       if(memberRepository.existsByEmail(requestDto.email())){

           throw  new IllegalArgumentException("등록된이메일이다"+ requestDto.email());
       }
       Member member = Member.builder()
               .name(requestDto.name())
               .email(requestDto.email())
               .phone(requestDto.phone())
               .grade(MemberGrade.BRONZE)
               .joinedAt(LocalDateTime.now())
               .build();
    Member saved = memberRepository.save(member);
       return MemberResponseDto.from(member);

    }


    public MemberResponseDto getMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() ->{
                    log.warn("emtity not found{}",id);
                  return   new IllegalArgumentException("회원을 찾을수없다: " + id);
                });
       log.info("member:{}",member);
        MemberResponseDto responseDto = MemberResponseDto.from(member);
      return   MemberResponseDto.from(member);


    }

    public List<MemberResponseDto> searchMembers(String name) {
        List<Member> byNameContaining = memberRepository.findByNameContaining(name);

        return getMemberResponseDtos(byNameContaining);


    }

    public List<MemberResponseDto> getAllMembers() {


        List<Member> all = memberRepository.findAll();
     /*   return all.stream()
                .map(MemberResponseDto::from)
                .toList();*/
      return   getMemberResponseDtos(all);

    }

    private  List<MemberResponseDto> getMemberResponseDtos(List<Member> byNameContaining) {
        return byNameContaining.stream()
                .map(MemberResponseDto::from)
                .collect(Collectors.toList());
    }

}
