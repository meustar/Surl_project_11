package com.koreait.surl_project_11.global.Rq;

import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope   // Scope -> 생명주기 관련.
@RequiredArgsConstructor
public class Rq {

    private final MemberService memberService;

    public Member getMember() {

        return memberService.getReferenceById(1L);  // 프록시 객체를 리턴한다.
    }
}
