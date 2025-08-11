package com.vocab.identity.jwt;

public record JwtTokenPair(
        String accessToken,
        String refreshToken,
        String accessTokenExpiry,
        String refreshTokenExpiry,
        String tokenType

) {

    public static JwtTokenPair of(
            String accessToken,
            String refreshToken,
            String accessTokenExpiry,
            String refreshTokenExpiry
    ) {
        return new JwtTokenPair(accessToken, refreshToken, accessTokenExpiry, refreshTokenExpiry, "Bearer");
    }
}
