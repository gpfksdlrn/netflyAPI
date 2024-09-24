package com.api.project.common.fillter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@Slf4j
public class SimpleCorsFilter implements Filter {

    public SimpleCorsFilter() {
        log.info("SimpleCORSFilter init");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req; // 요청을 HttpServletRequest로 변환
        HttpServletResponse response = (HttpServletResponse) res; // 응답을 HttpServletResponse로 변환
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin")); // 요청된 Origin 허용
        response.setHeader("Access-Control-Allow-Credentials", "true"); // 크로스 도메인 요청에서 사용자 인증 허용
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE"); // 허용되는 HTTP 메소드 명시
        response.setHeader("Access-Control-Max-Age", "3600"); // 프리플라이트 요청의 결과를 얼마나 오래 캐시할 것인지 시간(초) 명시
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me"); // 허용되는 HTTP 헤더 명시
        chain.doFilter(req, res); // 필터 체인을 통해 요청과 응답을 다음 필터나 대상 리소스로 전달
    }

    @Override
    public void init(FilterConfig filterConfig) {
        // 필터 초기화에 필요한 설정
    }

    @Override
    public void destroy() {
        // 필터 종료시 필요한 작업
    }

}