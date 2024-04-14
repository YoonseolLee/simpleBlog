package com.ryan.simpleBlog.config.jwt;

import com.ryan.simpleBlog.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;

    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    // 1. JWT 토근 생성 메서드
    private String makeToken(Date expiry, User user) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 typ : JWT
                // 내용 iss : gujueng32@naver.com
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now) // 내용 iat : 현재 시간
                .setExpiration(expiry) // 내용 exp : expiry 멤버 변숫값
                .setSubject(user.getEmail()) // 내용 sub : 유저의 이메일
                .claim("id", user.getId()) // 클레임 id : 유저 ID
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    /**
     * 2. JWT 토큰 유효성 검증 메서드
     * 복호화(decode) 과정에서 에러가 발생하면 유효하지 않은 토큰 -> false 반환
     */
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey()) // 비밀 값으로 복호화
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) { // 복호화 과정에서 에러가 나면 유효하지 않은 토큰으로 인식
            return false;
        }
    }

    /**
     * 3. 토큰 기반으로 인증 정보 객체 Authentication을 가져오는 메서드.
     * getClaims()를 호출해서 클레임 정보를 반환받아 사용자 이메일이 들어있는
     * 토큰 제목 sub와 토큰 기반으로 인증 정보를 생성
     *
     */
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.
                userdetails.User(claims.getSubject(), "", authorities), token, authorities);
    }

    /**
     * 4. 토큰 기반으로 유저 ID를 가져오는 메서드
     * 프로퍼티즈 파일에 저장한 비밀값으로 토큰을 복호화 한 뒤, 클레임을 가져오는 getClaims()를 호출해서
     * 클레임 정보를 반환받고 id 키로 저장된 값을 가져와 반환함.
     */
    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    /**
     * 프로퍼티즈 파일에 저장한 비밀값으로 토큰을 복호화한 뒤 클레임을 가져오는 메소드
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
