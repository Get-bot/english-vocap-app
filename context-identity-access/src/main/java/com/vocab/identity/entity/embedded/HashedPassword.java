package com.vocab.identity.entity.embedded;

import static com.vocab.identity.config.UserConst.MAX_PASSWORD_LENGTH;
import static com.vocab.identity.config.UserConst.MIN_PASSWORD_LENGTH;

import com.vocab.shared.exception.CustomException;
import com.vocab.shared.exception.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HashedPassword {

    @Column(name = "password", nullable = false)
    private String value;

    private HashedPassword(String hashedValue) {
        this.value = hashedValue;
    }

    public static HashedPassword encode(String rawPassword, PasswordEncoder passwordEncoder) {
        validateRawPassword(rawPassword);
        return new HashedPassword(passwordEncoder.encode(rawPassword));
    }

    public static HashedPassword fromHash(String hashedValue) {
        if (hashedValue == null || hashedValue.trim().isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_HASHED_PASSWORD);
        }
        return new HashedPassword(hashedValue);
    }

    private static void validateRawPassword(String rawPassword) {
        if (rawPassword == null || rawPassword.length() < MIN_PASSWORD_LENGTH) {
            throw new CustomException(ErrorCode.PASSWORD_TOO_SHORT);
        }
        if (rawPassword.length() > MAX_PASSWORD_LENGTH) {
            throw new CustomException(ErrorCode.PASSWORD_TOO_LONG);
        }
    }

    public boolean matches(String rawPassword, PasswordEncoder passwordEncoder) {
        if (rawPassword == null) {
            return false;
        }
        return passwordEncoder.matches(rawPassword, this.value);
    }
}