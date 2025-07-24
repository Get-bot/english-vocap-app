package com.vocab.identity.entity.embedded;

import static com.vocab.identity.config.UserConst.MAX_EMAIL_LENGTH;

import com.vocab.shared.exception.CustomException;
import com.vocab.shared.exception.ErrorCode;
import com.vocab.shared.util.FormatValidationUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailAddress {

    @Column(name = "email", nullable = false, unique = true)
    private String value;

    private EmailAddress(String email) {
        validateEmail(email);
        this.value = email;
    }

    public static EmailAddress of(String email) {
        return new EmailAddress(email);
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new CustomException(ErrorCode.EMAIL_REQUIRED);
        }
        if (!FormatValidationUtils.isValidEmail(email)) {
            throw new CustomException(ErrorCode.INVALID_EMAIL_FORMAT);
        }
        if (email.length() > MAX_EMAIL_LENGTH) {
            throw new CustomException(ErrorCode.INVALID_HASHED_PASSWORD);
        }
    }
}
