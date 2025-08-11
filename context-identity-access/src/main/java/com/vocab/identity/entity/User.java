package com.vocab.identity.entity;

import com.vocab.identity.entity.embedded.EmailAddress;
import com.vocab.identity.entity.embedded.HashedPassword;
import com.vocab.shared.entity.BaseEntity;
import com.vocab.shared.exception.CustomException;
import com.vocab.shared.exception.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles", // 중간 연결 테이블
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private final Set<Role> roles = new HashSet<>();
    private String username;
    @Embedded
    private EmailAddress email;
    @Embedded
    private HashedPassword password;
    @Column(nullable = false)
    private boolean enabled;

    @Builder
    private User(String username, EmailAddress email, HashedPassword password) {

        validateCreationInputs(username);

        this.username = username;
        this.email = email;
        this.password = password;

        this.enabled = true;
    }

    private static void validateCreationInputs(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new CustomException(ErrorCode.USER_NAME_REQUIRED);
        }
    }

    public void changePassword(String rawPassword, PasswordEncoder passwordEncoder) {
        this.password = HashedPassword.encode(rawPassword, passwordEncoder);
    }

    public void disable() {
        this.enabled = false;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }

    public void clearRoles() {
        this.roles.clear();
    }

    public boolean hasRole(String roleName) {
        if (roleName == null || roleName.trim().isEmpty()) {
            return false;
        }

        return this.roles.stream()
                .anyMatch(role -> roleName.equals(role.getName()));
    }
}
