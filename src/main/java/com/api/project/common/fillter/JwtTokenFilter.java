package com.api.project.common.fillter;

import com.api.project.common.entity.Member;
import com.api.project.common.exception.CustomException;
import com.api.project.common.repository.MemberRepository;
import com.api.project.common.utils.JwtTokenUtil;
import com.api.project.domain.auth.enums.AuthExceptionMessage;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

// OncePerRequestFilter : 매번 들어갈 때 마다 체크 해주는 필터
@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    private final String secretKey;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Header의 Authorization 값이 비어있으면 => Jwt Token을 전송하지 않음 => 로그인 하지 않음
        if(authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Header의 Authorization 값이 'Bearer '로 시작하지 않으면 => 잘못된 토큰
        if(!authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 전송받은 값에서 'Bearer ' 뒷부분(Jwt Token) 추출
        String token = authorizationHeader.split(" ")[1];

        // 전송받은 Jwt Token이 만료되었으면 => 다음 필터 진행(인증 X)
        if(JwtTokenUtil.isExpired(token, secretKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Jwt Token에서 로그인 유저 idx 추출
        Long loginMemberSeq = JwtTokenUtil.getLoginSeq(token, secretKey);

        // 추출한 loginId로 Member 찾아오기
        try {
            Member member = memberRepository.findById(loginMemberSeq)
                    .orElseThrow(() -> new CustomException(AuthExceptionMessage.NOT_FOUND_USER.toString()));

            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

            // loginUser 정보로 UsernamePasswordAuthenticationToken 발급
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    member.getMemberId(),null,authorities);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 권한 부여
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.error("유저 jwt 토큰 인증 중 오류가 발생했습니다! error:{}",e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
