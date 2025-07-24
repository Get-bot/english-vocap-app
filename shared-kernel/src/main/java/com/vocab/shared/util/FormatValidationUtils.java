package com.vocab.shared.util;

import static com.vocab.shared.util.PatternConst.EMAIL_PATTERN;

/**
 * 데이터 형식 검증 유틸리티 클래스
 */
public final class FormatValidationUtils {

    private FormatValidationUtils() {
    }

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

}
