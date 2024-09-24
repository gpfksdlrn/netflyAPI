package com.api.project.common.fillter;

import com.api.project.common.utils.NetworkUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingRequestFilter implements Filter {

    public static final String HTTP_METHOD = "X-HTTP-METHOD";
    public static final String HTTP_URL = "X-HTTP-URL";
    public static final String CLIENT_IP = "X-CLIENT-IP";
    public static final String HTTP_HEADER_TRANSACTION_ID = "X-HIT-TRANSACTION-ID";

    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getRequestURI().startsWith("/v3/api-docs/") || request.getRequestURI().startsWith("/docs") || request.getRequestURI().startsWith("/favicon.ico")) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        makePrintRequest(servletRequest);
        makeTransactionId(servletRequest, servletResponse);

        ContentCachingRequestWrapper requestToCache = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseToCache = new ContentCachingResponseWrapper(response);

        try {
            chain.doFilter(requestToCache, responseToCache);
        } finally {
            log.info("request body: {}", getRequestBody(requestToCache));
            log.info("response body: {}", getResponseBody(responseToCache));
            responseToCache.copyBodyToResponse();
        }
    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                try {
                    String requestBody = new String(buf, StandardCharsets.UTF_8);
                    Object parsedRequestBody = objectMapper.readValue(requestBody, Object.class);
                    return objectMapper.writeValueAsString(parsedRequestBody);
                } catch (JsonProcessingException e) {
                    log.error("Error parsing request body", e);
                }
            }
        }
        return "";
    }

    private String getResponseBody(final HttpServletResponse response) throws IOException {
        String payload = null;
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                payload = new String(buf, StandardCharsets.UTF_8);
                wrapper.copyBodyToResponse();
            }
        }

        return null == payload ? "" : payload;
    }

    private void makePrintRequest(ServletRequest servletRequest) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestURIWithQueryString = Optional.ofNullable(httpServletRequest.getRequestURI())
                .map(uri -> uri + Optional.ofNullable(httpServletRequest.getQueryString()).map(qs -> "?" + qs).orElse(""))
                .orElse("");

        MDC.put(HTTP_METHOD, httpServletRequest.getMethod());
        MDC.put(HTTP_URL, requestURIWithQueryString);
        MDC.put(CLIENT_IP, NetworkUtils.getClientIp(httpServletRequest));
    }

    private void makeTransactionId(ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String transactionId = Optional.ofNullable(req.getHeader(HTTP_HEADER_TRANSACTION_ID)).orElse(UUID.randomUUID().toString());

        MDC.put(HTTP_HEADER_TRANSACTION_ID, transactionId);

        HttpServletResponse res = (HttpServletResponse) servletResponse;
        res.addHeader(HTTP_HEADER_TRANSACTION_ID, transactionId);
    }
}
