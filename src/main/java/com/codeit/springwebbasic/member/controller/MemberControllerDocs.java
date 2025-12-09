package com.codeit.springwebbasic.member.controller;

import com.codeit.springwebbasic.common.dto.ApiResponse;
import com.codeit.springwebbasic.member.dto.response.MemberResponseDto;
import com.codeit.springwebbasic.member.dto.reuqest.MemberCreateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//swager 전용 인터페이스를 하나 선언해서 비즈니스 로직고 ㅏ문서 호 ㅏ로직분리
//기존 컨트롤러는 본연의 역할에 충실한다
@Tag(name = "사용자 관리 (Menber Controller)",description ="사용자 회우너가입,사용자조회 ,단건다건등을 관리하는api이다")
public interface MemberControllerDocs {
    @Operation(
            summary = "회원가입",
            description = """
                    새로운 사용자를 등록합니다 .
                    ##요청 데이터는
                    -이름 ,이메일 ,전화번호 정보가 필요합니다.
                    -모든 정보는 필수값입니다.
                    -이메일은 중복될 수 없습니다.
                    ##응답
                    -성공 시 저장된 회원 정보가 반환됩니다.
                    """


    )
    @ApiResponses(value = {
            //원래는아닌데 구분을지어주기위해서
            //웅답의 종류가 2개라그렇다
            //위는 정답일떄 , 아래는 비정상일때
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "회원가입 성공(이미지 등록 x)",
                    //스웨거 컨텐트
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                         {
                                             "code": "SUCESS",
                                             "message": "요청에 성공했습니다",
                                             "data": {
                                                 "id": 1,
                                                 "name": "김사랑",
                                                 "email": "qlqkehrw@sdsww.naber",
                                                 "phone": "010-144-2233",
                                                 "grade": "BRONZE",
                                                 "joinedAt": "2025-11-07T13:37:49.0257553"
                                             }
                                         }
                                         """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (이메일 중복 , 유효성 검사 실패 등)",
                    //스웨거 컨텐트
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                         {
                                            "code": "INVALID_ARGS",
                                            "message": "등록된이메일이다qlqkehrw@sdsww.naber",
                                            "data": null
                                         }
                                         """
                            )
                    )
            )
    })
     ResponseEntity<ApiResponse<MemberResponseDto>> createMember(

             @Valid @RequestBody MemberCreateRequestDto reuqestDto)
            ;

    ResponseEntity<ApiResponse<MemberResponseDto>> getMember(@PathVariable Long id);


   //dto josn로 전달받은 데이터는 직접 ㅡㄹ래스에 @schema적용
    //낱개로 전달되는 데이터는 @parameter로 설명 추가
    ResponseEntity<ApiResponse<List<MemberResponseDto>>> getMembers(
            @Parameter(description = "회원 조회 이름",required = false)
            @RequestParam(required = false) String name
    );

}
