package com.koreait.surl_project_11.domain.member.member.service;
import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.member.member.repository.MemberRepository;
import com.koreait.surl_project_11.global.exceptions.GlobalException;
import com.koreait.surl_project_11.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public RsData<Member> join(String username, String password, String nickname) {

//        boolean present = findByUsername(username).isPresent();

//        if (present) {
            // V1
//            return RsData.of("400-1", "이미 존재하는 아이디입니다.", Member.builder().build());
            // V2
//            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
            // V3
//            throw new GlobalException("400-1","이미 존재하는 아이디입니다.");
//        }

            // V4
        findByUsername(username).ifPresent(ignored -> {
            throw new GlobalException("400-1", "이미 존재하는 아이디 입니다.");
        });

        Member member = Member.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .build();

        memberRepository.save(member);

        return RsData.of("회원가입이 완료되었습니다.", member);
    }

    private Optional<Member> findByUsername(String username) {

        return memberRepository.findByUsername(username);
    }
}