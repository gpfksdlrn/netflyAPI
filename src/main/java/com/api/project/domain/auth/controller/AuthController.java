package com.api.project.domain.auth.controller;

import com.api.project.domain.auth.controller.req.AuthSigninReq;
import com.api.project.domain.auth.controller.req.AuthTokenRefreshReq;
import com.api.project.CommonResponse;
import com.api.project.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping(AuthController.Url.ROOT)
@RestController
@RequiredArgsConstructor
public class AuthController {
    public static class Url {
        public static final String ROOT = "/v1/api/auth";
        public static final String AUTH_REFRESH_TOKEN = "/refresh/token"; // 토큰 갱신 엔드포인트 추가
        public static final String AUTH_SIGNIN = "/signin";
    }

    private final AuthService authService;

    /**
     * 로그인 API
     */
    @PostMapping(Url.AUTH_SIGNIN)
    public CommonResponse authSignin(HttpServletResponse response, @RequestBody @Valid AuthSigninReq dto) {
        return authService.authSignin(response,dto);
    }

    /**
     * 토큰 갱신 API
     */
    @PostMapping(Url.AUTH_REFRESH_TOKEN)
    public CommonResponse refreshToken(HttpServletResponse response, @RequestBody @Valid AuthTokenRefreshReq dto) {
        return authService.refreshToken(response,dto);
    }
}