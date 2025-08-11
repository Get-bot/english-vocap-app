package com.vocab.identity.security;

import com.vocab.identity.entity.User;
import com.vocab.shared.security.AuthenticatedUser;
import java.util.Collection;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails, AuthenticatedUser {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword().getValue();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    public Long getId() {
        return user.getId();
    }

    public String getEmail() {
        return user.getEmail().getValue();
    }

    @Override
    public Set<String> getRoles() {
        return user.getRoles().stream()
                .map(role -> role.getName().startsWith("ROLE_") ? role.getName() : "ROLE_" + role.getName())
                .collect(java.util.stream.Collectors.toSet());
    }

    // Role check 헬퍼 메소드
    public boolean hasRole(String roleName) {
        if (roleName == null) {
            return false;
        }
        String finalRoleName = roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName;
        return user.hasRole(finalRoleName);
    }

    public boolean hasAnyRole(String... roleNames) {
        if (roleNames == null) {
            return false;
        }
        for (String roleName : roleNames) {
            if (hasRole(roleName)) {
                return true;
            }
        }
        return false;
    }
}
