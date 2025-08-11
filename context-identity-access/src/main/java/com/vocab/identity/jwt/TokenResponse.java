package com.vocab.identity.jwt;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vocab.identity.dto.reponse.UserInfo;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record TokenResponse(
        String accessToken,
        String refreshToken,
        String tokenType,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime accessTokenExpiry,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime refreshTokenExpiry,

        UserInfo userInfo
) {

}
