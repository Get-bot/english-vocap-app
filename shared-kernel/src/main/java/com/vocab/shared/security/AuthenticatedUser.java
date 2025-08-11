package com.vocab.shared.security;

import java.util.Set;

public interface AuthenticatedUser {

    Long getId();

    String getUsername();

    String getEmail();

    Set<String> getRoles(); // 구체적인 Role 클래스가 아닌, 단순 문자열 Set

    boolean hasRole(String roleName);
}