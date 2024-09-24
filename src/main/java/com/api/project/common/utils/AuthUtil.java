package com.api.project.common.utils;

import org.springframework.http.HttpHeaders;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthUtil {
    private static final String AUTH_HEADER_NAME = "Authorization";
    private static final String BEARER = "Bearer ";

    /*
     * SHA-512 방식 암호화 적용
     */
    public static String encrypt(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(text.getBytes());

        return bytesToHex(md.digest());
    }

    /*
     * 암호화 hex
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    /*
     * 인증 헤더 만들기
     */
    public static HttpHeaders createAuthHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTH_HEADER_NAME, BEARER + token);
        return headers;
    }
}
