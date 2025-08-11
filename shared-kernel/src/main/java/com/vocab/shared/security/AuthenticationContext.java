package com.vocab.shared.security;

import com.vocab.shared.exception.CustomException;
import com.vocab.shared.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationContext {

    public AuthenticatedUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof AuthenticatedUser) {
            return (AuthenticatedUser) principal;
        }

        throw new CustomException(ErrorCode.USER_PRINCIPAL_ERROR);
    }
}