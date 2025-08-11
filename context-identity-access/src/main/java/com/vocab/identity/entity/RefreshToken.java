package com.vocab.identity.entity;

import com.vocab.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "refresh_tokens", indexes = {
        @Index(name = "idx_refresh_token", columnList = "tokenValue"),
        @Index(name = "idx_user_id", columnList = "userId")
})
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class RefreshToken extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String tokenValue;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private String deviceInfo;
    private String ipAddress;

    @Column(nullable = false)
    private boolean revoked = false;

    private LocalDateTime lastUsedAt;
    private int useCount = 0;

    // 팩토리 메서드
    public static RefreshToken create(
            Long userId,
            String deviceInfo,
            String ipAddress,
            long expirationDays
    ) {
        RefreshToken token = new RefreshToken();
        token.tokenValue = UUID.randomUUID().toString();
        token.userId = userId;
        token.deviceInfo = deviceInfo;
        token.ipAddress = ipAddress;
        token.expiresAt = LocalDateTime.now().plusDays(expirationDays);
        token.revoked = false;
        token.useCount = 0;
        return token;
    }

    // 비지니스 메서드
    // === 비즈니스 메서드 ===

    /**
     * 토큰 사용 기록
     */
    public void recordUsage() {
        this.lastUsedAt = LocalDateTime.now();
        this.useCount++;
    }

    /**
     * 토큰 폐기
     */
    public void revoke() {
        this.revoked = true;
    }

    /**
     * 토큰 순환 (Rotation) - 보안을 위해 사용할 때마다 새 토큰 발급
     */
    public RefreshToken rotate(long expirationDays) {
        this.revoke();
        return RefreshToken.create(
                this.userId,
                this.deviceInfo,
                this.ipAddress,
                expirationDays
        );
    }

    // === 상태 확인 메서드 ===

    public boolean isValid() {
        return !revoked && !isExpired();
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

}
