package com.vocab.identity.service;

import com.vocab.identity.dto.request.RegisterRequest;
import com.vocab.identity.entity.User;
import com.vocab.identity.entity.embedded.EmailAddress;
import com.vocab.identity.entity.embedded.HashedPassword;
import com.vocab.identity.jwt.JwtAuthService;
import com.vocab.identity.jwt.TokenResponse;
import com.vocab.identity.repository.UserRepository;
import com.vocab.shared.exception.CustomException;
import com.vocab.shared.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final JwtAuthService jwtAuthService;

    public TokenResponse register(RegisterRequest request, String ipAddress) {

        EmailAddress emailAddress = EmailAddress.of(request.email());
        if(userRepository.existsByEmail(emailAddress)) {
            log.warn("Attempt to register with existing email: {}", request.email());
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        HashedPassword password = HashedPassword.encode(request.password(), jwtAuthService.getPasswordEncoder());
        User user = User.builder()
                .username(request.name())
                .email(emailAddress)
                .password(request.password())
                .build();

    }
        log.info("Registering user with email: {}", request.email());


}
