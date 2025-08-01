package com.vocab.identity.entity;

public enum Role {
    USER("일반 사용자"),
    ADMIN("관리자");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}