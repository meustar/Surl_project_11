package com.koreait.surl_project_11.domain.member.member.controller;

import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.member.member.service.MemberService;
import com.koreait.surl_project_11.global.exceptions.GlobalException;
import com.koreait.surl_project_11.global.rsData.RsData;
import com.koreait.surl_project_11.standard.util.Ut;
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
        private String username;
        private String password;
        private String nickname;
    }

    // POST /api/v1/members/join
    @PostMapping("")   // "/join" 생략 가능하다. why? POST 때문에.
    public RsData<Member> join(
//            String username, String password, String nickname
            @RequestBody MemberJoinReqBody requestBody
    ) {
        if (Ut.str.isBlank(requestBody.username)) {
            throw new GlobalException("400-1", "username을 입력해");
        }
        if (Ut.str.isBlank(requestBody.password)) {
            throw new GlobalException("400-2", "password을 입력해");
        }
        if (Ut.str.isBlank(requestBody.nickname)) {
            throw new GlobalException("400-3", "nickname을 입력해");
        }

        return memberService.join(requestBody.username, requestBody.password, requestBody.nickname);

    }

    @GetMapping("/testThrowIllegalArgumentException")
    @ResponseBody
    public RsData<Member> testThrowIllegalArgumentException() {
        throw new IllegalArgumentException("IllegalArgumentException");
    }
}
