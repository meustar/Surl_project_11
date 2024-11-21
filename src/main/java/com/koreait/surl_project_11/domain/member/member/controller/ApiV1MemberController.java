package com.koreait.surl_project_11.domain.member.member.controller;

import com.koreait.surl_project_11.domain.member.member.dto.MemberDto;
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
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Slf4j
public class ApiV1MemberController {
    private final MemberService memberService;


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
        MemberDto item;

    }

    @PostMapping("")
    public RsData<MemberJoinRespBody> join(
            @RequestBody @Valid MemberJoinReqBody requestBody
    ) {

        RsData<Member> joinRs = memberService.join(requestBody.username, requestBody.password, requestBody.nickname);

        return joinRs.newDataOf(
                new MemberJoinRespBody(
                        new MemberDto(
                                joinRs.getData()
                        )
                )
        );

    }

    @GetMapping("/testThrowIllegalArgumentException")
    @ResponseBody
    public RsData<Member> testThrowIllegalArgumentException() {
        throw new IllegalArgumentException("IllegalArgumentException");
    }
}
