package com.koreait.surl_project_11.domain.member.member.repository;

import com.koreait.surl_project_11.domain.member.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    // 조회 결과가 최대 1개 일 경우 optional.
    // 그 외의 경우에는 List를 리턴하는것이 관례.

    Optional<Member> findByRefreshToken(String refreshToken);
}