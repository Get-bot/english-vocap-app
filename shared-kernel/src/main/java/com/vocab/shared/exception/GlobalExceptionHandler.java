package com.vocab.shared.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ProblemDetail> handleCustomException(CustomException e) {

        ErrorCode errorCode = e.getErrorCode();
        HttpStatus httpStatus = errorCode.getStatus();

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, e.getMessage());
        problemDetail.setTitle(errorCode.name());

        return ResponseEntity.status(httpStatus).body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGlobalException(Exception e) {

        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        HttpStatus httpStatus = errorCode.getStatus();

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, errorCode.getMessage());
        problemDetail.setTitle(errorCode.name());

        log.error("Unhandled exception occurred: {}", e.getMessage(), e);

        return ResponseEntity.status(httpStatus).body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {

        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        HttpStatus httpStatus = errorCode.getStatus();

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, errorCode.getMessage());
        problemDetail.setTitle(errorCode.name());

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        problemDetail.setProperty("errors", errors);

        return ResponseEntity.status(httpStatus).body(problemDetail);
    }
}
