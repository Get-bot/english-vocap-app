package com.vocab.identity.jwt;

import com.vocab.identity.entity.User;

public interface JwtAuthService {

    /**
     * 사용자 인증 후 토큰 발급
     */
    JwtTokenPair authenticateAndGenerateTokens(User user, String deviceInfo, String ipAddress);

    /**
     * 리프레시 토큰으로 새 액세스 토큰 발급
     */
    JwtTokenPair refreshAccessToken(String refreshToken, String ipAddress);

    /**
     * 토큰 폐기
     */
    void revokeToken(String refreshToken);

    /**
     * 사용자의 모든 토큰 폐기
     */
    void revokeAllUserTokens(Long userId);
}
