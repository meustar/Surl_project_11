package com.koreait.surl_project_11.domain.member.member.controller;

import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.member.member.service.MemberService;
import com.koreait.surl_project_11.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")  // 복수개로 명명하는것이 관례
@RequiredArgsConstructor
@Slf4j
public class ApiV1MemberController {
    private final MemberService memberService;

    //CRUD

    // 요청 양식
    @AllArgsConstructor
    @Getter
    public static class MemberJoinReqBody{
        @NotBlank   // (공백금지)
        private String username;
        @NotBlank
        private String password;
        @NotBlank
        private String nickname;
    }

    // 응답 양식
    @AllArgsConstructor
    @Getter
    public static class MemberJoinRespBody{
        Member item;    // 단수로 표현.

    }

    // End_point & Action method
    // POST /api/v1/members/join
    @PostMapping("")   // "/join" 생략 가능하다. why? POST 때문에.
    public RsData<MemberJoinRespBody> join(
//            String username, String password, String nickname
            @RequestBody @Valid MemberJoinReqBody requestBody
    ) {

        //  리턴값이 <MemberJoinRespBody> 일때 v1.
//        RsData<Member> joinRs = memberService.join(requestBody.username, requestBody.password, requestBody.nickname);
//        Member member = joinRs.getData();
//
//        return RsData.of(new MemberJoinRespBody(member));

        RsData<Member> joinRs = memberService.join(requestBody.username, requestBody.password, requestBody.nickname);

//        return joinRs; // (리턴값이 Member 일때)

        return joinRs.newDataOf(
                new MemberJoinRespBody(joinRs.getData())
        );

    }

    @GetMapping("/testThrowIllegalArgumentException")
    @ResponseBody
    public RsData<Member> testThrowIllegalArgumentException() {
        throw new IllegalArgumentException("IllegalArgumentException");
    }
}
