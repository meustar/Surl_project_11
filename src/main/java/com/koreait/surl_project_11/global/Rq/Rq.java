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

    private Member member;

//    @Getter
//    @Setter
//    private Member member;

    public Member getMember() {
        if(member != null) return member;   // 캐시 데이터 방식. == 메모리 캐싱

        // 1. Url parameter에서 받아오는 방법.
        String actorUsername = req.getParameter("actorUsername"); // HttpServletRequest req => @RequestScope  .. Rq는 @RequestScope의 Bean이다.
        String actorPassword = req.getParameter("actorPassword");

        if (actorUsername == null || actorPassword == null) {
            String authorization = req.getHeader("Authorization");
            if(authorization != null) {
                authorization = authorization.substring("bearer ".length());
                String[] authorizationBits = authorization.split(" ", 2);
                actorUsername = authorizationBits[0];
                actorPassword = authorizationBits.length == 2 ? authorizationBits[1] : null;
            }
        }

        // 3. 파라미터와 해더를 같이쓰는 방법.
//        if(actorUsername == null) actorUsername = req.getHeader("actorUsername");
//        if(actorPassword == null) actorPassword = req.getHeader("actorPassword");

        // 1. header에서 받아오는 방법.
//        actorUsername = req.getHeader("actorUsername");
//        actorPassword = req.getHeader("actorPassword");

        if(Ut.str.isBlank(actorUsername)) throw new GlobalException("401-1", "인증정보(아이디) 입력해주세요.");
        if(Ut.str.isBlank(actorPassword)) throw new GlobalException("401-2", "인증정보(비밀번호) 입력해주세요.");

        Member loginedMember = memberService.findByUsername(actorUsername).orElseThrow(() -> new GlobalException("403-3", "해당 회원은 없습니다."));
        if(!loginedMember.getPassword().equals(actorPassword)) throw new GlobalException("403-4", "비밀번호가 틀렸습니다.");

        member = loginedMember;

        return loginedMember;
    }

    public String getCurrentUrlPath() {
        return req.getRequestURI();
    }

    public void setStatusCode(int statusCode) {
        resp.setStatus(statusCode);
    }
}
