package com.codeit.springwebbasic.member.service;

import com.codeit.springwebbasic.member.MemberGrade;
import com.codeit.springwebbasic.member.dto.response.MemberResponseDto;
import com.codeit.springwebbasic.member.dto.reuqest.MemberCreateRequestDto;
import com.codeit.springwebbasic.member.entity.Member;
import com.codeit.springwebbasic.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
  private final S3FileService s3FileService;

   public MemberResponseDto createMember(MemberCreateRequestDto requestDto, MultipartFile file){

       if(memberRepository.existsByEmail(requestDto.email())){
           throw  new IllegalArgumentException("등록된이메일이다"+ requestDto.email());
       }

       //aws s3와 연동해서  프로필파일을 전송하는 로직 작성 하려고 한다
       try {
           s3FileService.uploadTos3Bucket(file);
       } catch (IOException e) {
           log.error(e.getMessage());
           throw new RuntimeException("넣기에실패",e);
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
