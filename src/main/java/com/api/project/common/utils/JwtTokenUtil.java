package com.api.project.common.utils;
import com.api.project.common.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class JwtTokenUtil {
    public static String createToken(Member member) {
        // Claim = Jwt Token에 들어갈 정보
        // Claim에 loginId를 넣어 줌으로써 나중에 loginId를 꺼낼 수 있음
        Claims claims = Jwts.claims();
        claims.put("member_seq",member.getId());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EncryptionUtil.exp))
                .signWith(SignatureAlgorithm.HS256, EncryptionUtil.key)
                .compact();
    }

    // Claims에서 login한 유저의 idx 꺼내기
    public static Long getLoginSeq(String token, String secretKey) {
        Number memberSeq = extractClaims(token, secretKey).get("memberSeq", Number.class);
        return memberSeq.longValue(); // Long 타입으로 변환
    }

    // 발급된 Token이 만료 시간이 지났는지 체크
    public static boolean isExpired(String token, String secretKey) {
        Date expiredDate = extractClaims(token, secretKey).getExpiration();
        // Token의 만료 날짜가 지금보다 이전인지 check
        return expiredDate.before(new Date());
    }


    // SecretKey를 사용해 Token Parsing
    private static Claims extractClaims(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}
