package com.codeit.springwebbasic.member.controller;






        // 회원 가입
        // url: /api/members: POST
        // 데이터: name: string(필수), email: string(필수), phone: string(필수, 전번 형식 검사 필요)
        // 요청 DTO: MemberCreateRequestDto
        // 비즈니스로직: 이메일 중복 체크 필요, DTO를 Entity로 변환해서 멤버 저장
        // 응답: id, name, email, phone, grade, joinedAt
        // 상태 코드: 201 CREATED


        // 회원 조회 (단일)
        // url: /api/members/멤버id: GET
        // 비즈니스로직: 회원 조회 후 리턴, 회원 없을 시 "회원을 찾을 수 없습니다." | 400 응답
        // 응답: 위에서 사용한 Response용 DTO로 응답 | 200 OK


        // 전체 회원 조회 & 검색
        // url: /api/members?name=xxx  | name은 전달되지 않을 수도 있습니다.
        // name이 전달되지 않으면 전체 조회, name이 전달 된다면 name이 포함된 회원을 조회.
        // 비즈니스로직: 각 상황에 맞는 Service 메서드를 호출해서 리턴.
        // 응답: 조회된 회원(Response DTO)을 리스트에 담아서 리턴 | 200 OK


import com.codeit.springwebbasic.common.dto.ApiResponse;
import com.codeit.springwebbasic.member.dto.response.MemberResponseDto;
import com.codeit.springwebbasic.member.dto.reuqest.MemberCreateRequestDto;
import com.codeit.springwebbasic.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@Slf4j

public class MemberController implements MemberControllerDocs {


    //@RequiredArgsConstructor이걸위한
    private final MemberService memberService;


    // 회원 가입
    // url: /api/members: POST
    // 데이터: name: string(필수), email: string(필수), phone: string(필수, 전번 형식 검사 필요)
    // 요청 DTO: MemberCreateRequestDto
    // 비즈니스로직: 이메일 중복 체크 필요, DTO를 Entity로 변환해서 멤버 저장
    // 응답: id, name, email, phone, grade, joinedAt
    // 상태 코드: 201 CREATED

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<MemberResponseDto>> createMember(
            @Valid @RequestPart("request") MemberCreateRequestDto reuqestDto,
            @RequestPart("file") MultipartFile file
            ){
        log.info("posSt dto:{}",reuqestDto);
        MemberResponseDto responseDto = memberService.createMember(reuqestDto,file);


        ApiResponse<MemberResponseDto> response = ApiResponse.success(responseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 회원 조회 (단일)
    // url: /api/members/멤버id: GET
    // 비즈니스로직: 회원 조회 후 리턴, 회원 없을 시 "회원을 찾을 수 없습니다." | 400 응답
    // 응답: 위에서 사용한 Response용 DTO로 응답 | 200 OK

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberResponseDto>> getMember(@PathVariable Long id){
        MemberResponseDto
                member = memberService.getMember(id);
        //스테이터스에 바디넣어서 채워넣는다


        return ResponseEntity
                .status(HttpStatus.OK).body(ApiResponse.success(member));
    }

    // 전체 회원 조회 & 검색
    // url: /api/members?name=xxx  | name은 전달되지 않을 수도 있습니다.
    // name이 전달되지 않으면 전체 조회, name이 전달 된다면 name이 포함된 회원을 조회.
    // 비즈니스로직: 각 상황에 맞는 Service 메서드를 호출해서 리턴.
    // 응답: 조회된 회원(Response DTO)을 리스트에 담아서 리턴 | 200 OK

    @GetMapping()
    public ResponseEntity<ApiResponse<List<MemberResponseDto>>> getMembers(@RequestParam(required = false) String name){

        List<MemberResponseDto> members
                = name != null
                ? memberService.searchMembers(name) : memberService.getAllMembers();


        return ResponseEntity.ok(ApiResponse.success(members));

    }





}
