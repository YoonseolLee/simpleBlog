package com.ryan.simpleBlog.service;

import com.ryan.simpleBlog.config.jwt.TokenProvider;
import com.ryan.simpleBlog.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    /**
     * 전달받은 리프레시 토큰으로 토큰 유효성을 검사하고,
     * 유효한 토큰이면 리프레시 토큰으로 사용자 ID를 찾는다.
     * 사용자 ID로 사용자를 찾은 다음,토큰 제공자의 generateToken()을 호출하여 새로운 액세스 토큰을 만든다.
     */

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken) {
        // 토큰 유효성 검사에 실패하면 예외 발생
        if (!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
