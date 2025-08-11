package com.vocab.shared.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "입력 값이 올바르지 않습니다."),

    // USER
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 회원을 찾을 수 없습니다."),
    USER_DISABLED(HttpStatus.FORBIDDEN, "해당 회원은 비활성화 상태입니다."),
    USER_NAME_REQUIRED(HttpStatus.BAD_REQUEST, "사용자 이름은 필수 입력 항목입니다."),
    USER_PRINCIPAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "사용자 정보가 올바르지 않습니다."),

    // Password
    PASSWORD_TOO_SHORT(HttpStatus.BAD_REQUEST, "비밀번호는 최소 6자리 이상이어야 합니다."),
    PASSWORD_TOO_LONG(HttpStatus.BAD_REQUEST, "비밀번호는 100자를 초과할 수 없습니다."),
    INVALID_HASHED_PASSWORD(HttpStatus.INTERNAL_SERVER_ERROR, "해시된 비밀번호 값이 유효하지 않습니다."),
    PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),

    // Email
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "유효하지 않은 이메일 형식입니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이메일을 가진 사용자를 찾을 수 없습니다."),
    EMAIL_REQUIRED(HttpStatus.BAD_REQUEST, "이메일은 필수 입력 항목입니다."),
    EMAIL_TOO_LONG(HttpStatus.BAD_REQUEST, "이메일은 255자를 초과할 수 없습니다."),

    // Roles
    ROLE_REQUIRED(HttpStatus.BAD_REQUEST, "권한은 필수 입력 항목입니다."),

    // 500 INTERNAL_SERVER_ERROR
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;
}
