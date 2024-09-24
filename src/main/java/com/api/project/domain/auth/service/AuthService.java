package com.api.project.domain.auth.service;

import com.api.project.CommonResponse;
import com.api.project.common.entity.Member;
import com.api.project.common.exception.CustomException;
import com.api.project.common.repository.MemberRepository;
import com.api.project.common.utils.EncryptionUtil;
import com.api.project.common.utils.JwtTokenUtil;
import com.api.project.domain.auth.controller.req.AuthSigninReq;
import com.api.project.domain.auth.controller.req.AuthTokenRefreshReq;
import com.api.project.domain.auth.enums.AuthExceptionMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class AuthService {
    private final MemberRepository memberRepository;

    public CommonResponse authSignin(HttpServletResponse response, AuthSigninReq dto) {
        Member member = memberRepository.findByMemberIdAndMemberPw(dto.getId(), dto.getPw())
                .orElseThrow(() -> new CustomException(AuthExceptionMessage.SIGIN_NOT_FOUND_MESSAGE.toString()));

        if (member == null) {
            throw new CustomException(AuthExceptionMessage.SIGNIN_ERROR_MESSAGE.toString());
        }

        String token = JwtTokenUtil.createToken(member);
        Cookie cookie = new Cookie(AUTHORIZATION, token);
        cookie.setPath("/");
        response.addCookie(cookie);

        return CommonResponse.responseSuccess();
    }

    public CommonResponse refreshToken(HttpServletResponse response, AuthTokenRefreshReq dto) {
        // Header의 Authorization 값이 'Bearer '로 시작하지 않으면 => 잘못된 토큰
        if (!dto.getRefreshToken().startsWith("Bearer ")) {
            log.error("Bearer 로 시작하지 않는 토큰 : {}", dto.getRefreshToken());
            throw new CustomException(AuthExceptionMessage.TOKEN_REFRESH_ERROR_MESSAGE.toString());
        }

        try {
            // 전송받은 값에서 'Bearer ' 뒷부분(Jwt Token) 추출
            String token = dto.getRefreshToken().split(" ")[1];
            Long memberSeq = JwtTokenUtil.getLoginSeq(token, EncryptionUtil.key);
            Member member = memberRepository.findById(memberSeq)
                    .orElseThrow(() -> new CustomException(AuthExceptionMessage.USER_NOT_FOUND_MESSAGE.toString()));

            String JwtToken = JwtTokenUtil.createToken(member);
            Cookie cookie = new Cookie(AUTHORIZATION, JwtToken);
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (Exception e) {
            log.error("유저 jwt 토큰 인증 중 오류가 발생했습니다 error:{}", e.getMessage());
            throw new CustomException(AuthExceptionMessage.TOKEN_REFRESH_ERROR_MESSAGE.toString());
        }

        return CommonResponse.responseSuccess();
    }
}
