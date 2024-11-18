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
    @AllArgsConstructor
    @Getter
    public static class MemberJoinReqBody{
        @NotBlank(message = "username 입력하세요.")   // (공백금지)
        private String username;
        @NotBlank(message = "password 입력하세요.")
        private String password;
        @NotBlank(message = "nickname 입력하세요.")
        private String nickname;
    }

    // POST /api/v1/members/join
    @PostMapping("")   // "/join" 생략 가능하다. why? POST 때문에.
    public RsData<Member> join(
//            String username, String password, String nickname
            @RequestBody @Valid MemberJoinReqBody requestBody
    ) {

        return memberService.join(requestBody.username, requestBody.password, requestBody.nickname);

    }

    @GetMapping("/testThrowIllegalArgumentException")
    @ResponseBody
    public RsData<Member> testThrowIllegalArgumentException() {
        throw new IllegalArgumentException("IllegalArgumentException");
    }
}
