package com.koreait.surl_project_11.global.Rq;

import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.member.member.service.MemberService;
import com.koreait.surl_project_11.global.exceptions.GlobalException;
import com.koreait.surl_project_11.standard.util.Ut;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope   // Scope -> 생명주기 관련.
@RequiredArgsConstructor
public class Rq {

    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final MemberService memberService;

//    @Getter
//    @Setter
//    private Member member;

    public Member getMember() {
        String actorUsername = req.getParameter("actorUsername"); // HttpServletRequest req => @RequestScope  .. Rq는 @RequestScope의 Bean이다.

        if(Ut.str.isBlank(actorUsername)) throw new GlobalException("401-1", "인증정보 입력해주세요.");

        Member loginedMember = memberService.findByUsername(actorUsername).orElseThrow(() -> new GlobalException("401-2", "올바르지 않는 인증정보 입니다."));

        return loginedMember;
    }

    public String getCurrentUrlPath() {
        return req.getRequestURI();
    }

    public void setStatusCode(int statusCode) {
        resp.setStatus(statusCode);
    }
}
