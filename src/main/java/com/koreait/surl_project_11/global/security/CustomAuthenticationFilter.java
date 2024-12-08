package com.koreait.surl_project_11.global.security;

import com.koreait.surl_project_11.domain.auth.auth.service.AuthTokenService;
import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.member.member.service.MemberService;
import com.koreait.surl_project_11.global.Rq.Rq;
import com.koreait.surl_project_11.global.app.AppConfig;
import com.koreait.surl_project_11.standard.util.Ut;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final MemberService memberService;
    private final Rq rq;
    private final AuthTokenService authTokenService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) {

//        String apiKey = rq.getCookieValue("apiKey", null);
        String accessToken = rq.getCookieValue("accessToken", null);
        String refreshToken = rq.getCookieValue("refreshToken", null);

        if (accessToken == null || refreshToken == null) {
            String authorization = req.getHeader("Authorization");

            if (authorization != null) {
//                accessToken  = authorization.substring("bearer ".length());
                String[] authorizationBits = authorization.substring("bearer ".length()).split(" ", 2);
                if (authorizationBits.length == 2) {
                    accessToken = authorizationBits[0];
                    refreshToken = authorizationBits[1];
                }
            }
        }

        if (Ut.str.isBlank(accessToken) || Ut.str.isBlank(refreshToken)) {
            filterChain.doFilter(req, resp);
            return;
        }

        // 엑세스 토큰 만료시 refreshToken으로 새로 교체 - 유효시간 마다
        if (!authTokenService.validateToken(accessToken)) {
//            filterChain.doFilter(req, resp);
//            return;
            Member member = memberService.findByRefreshToken(refreshToken).orElse(null);

            if (member == null) {
                filterChain.doFilter(req, resp);
                return;
            }
            // 만료되면 자동적으로 갱신.
            String newAccessToken = authTokenService.genToken(member, AppConfig.getAccessTokenExpirationSec());
            rq.setCookie("accessToken", newAccessToken);
            log.debug("accessToken renewed: {}", newAccessToken);

            accessToken = newAccessToken;

        }

        Map<String, Object> accessTokenData = authTokenService.getDataFrom(accessToken);
        long id = (int) accessTokenData.get("id");
        User user = new User(id + "", "", List.of());

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(req, resp);
    }
}