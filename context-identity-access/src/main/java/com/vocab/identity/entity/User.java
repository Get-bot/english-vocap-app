package com.vocab.identity.entity;

import com.vocab.identity.entity.embedded.EmailAddress;
import com.vocab.identity.entity.embedded.HashedPassword;
import com.vocab.shared.entity.BaseEntity;
import com.vocab.shared.exception.CustomException;
import com.vocab.shared.exception.ErrorCode;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    private String username;

    @Embedded
    private EmailAddress email;

    @Embedded
    private HashedPassword password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private List<Role> roles = new ArrayList<>();

    @Column(nullable = false)
    private boolean enabled;

    @Builder
    private User(String username, EmailAddress email, HashedPassword password, String nickname) {

        validateCreationInputs(username);

        this.username = username;
        this.email = email;
        this.password = password;

        // 기본값 설정
        this.roles = List.of(Role.USER);
        this.enabled = true;
    }

    private static void validateCreationInputs(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new CustomException(ErrorCode.USER_NAME_REQUIRED);
        }
    }

    public void changeRoles(List<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            throw new CustomException(ErrorCode.ROLE_REQUIRED);
        }
        this.roles = new ArrayList<>(roles);
    }

    public void changePassword(String rawPassword, PasswordEncoder passwordEncoder) {
        this.password = HashedPassword.encode(rawPassword, passwordEncoder);
    }

    public void disable() {
        this.enabled = false;
    }
}
