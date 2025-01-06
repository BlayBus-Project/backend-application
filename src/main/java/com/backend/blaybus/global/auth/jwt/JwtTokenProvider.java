package com.backend.blaybus.global.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;
    private final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${springboot.jwt.secret}")
    private String secretKey;
    private Key key;
    private final long tokenValidMillisecond = 1000L * 60 * 60; // 1시간

    public JwtTokenProvider(@Lazy UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        logger.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)); // 비밀 키 생성
        logger.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
    }

    public String createToken(String email, String name, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("fullName", name);
        claims.put("roles", roles);

        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(key) // 변경된 부분: secretKey 대신 key를 사용
                .compact();
        logger.info("[createToken] 토큰 생성 완료");
        return token;
    }

    public Authentication getAuthentication(String token) {
        logger.info("[getAuthentication] 토큰 인증 정보 조회 시작");
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));
        logger.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails userName: {}", userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        logger.info("[getUsername] 토큰에서 회원 구별 정보 추출");
        // parser() 대신 parserBuilder()를 사용하여 키 설정
        String info = Jwts.parserBuilder() // 변경된 부분
                .setSigningKey(key) // 변경된 부분
                .build() // 변경된 부분
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        logger.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료, info: {}", info);
        return info;
    }

    public String resolveToken(HttpServletRequest request) {
        logger.info("[resolveToken] HTTP 헤더에서 Token 값 추출");

        String header = request.getHeader("Authorization");
        logger.info("Header value: {}", header);

        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);// "Bearer " 부분을 제거하고 토큰만 반환
        }

        return header;
    }

    public boolean validationToken(String token) {
        logger.info("[validateToken] 토큰 유효 체크 시작");
        try {
            // parser() 대신 parserBuilder()를 사용하여 키 설정
            Jws<Claims> claims = Jwts.parserBuilder() // 변경된 부분
                    .setSigningKey(key) // 변경된 부분
                    .build() // 변경된 부분
                    .parseClaimsJws(token);
            logger.info("[validateToken] 토큰 유효 체크 완료");

            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            logger.error("[validateToken] 만료된 토큰: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("[validateToken] 지원되지 않는 토큰: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("[validateToken] 잘못된 토큰: {}", e.getMessage());
        } catch (SignatureException e) {
            logger.error("[validateToken] 서명 검증 실패: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("[validateToken] 예기치 않은 오류: {}", e.getMessage());
        }
        return false;
    }
}