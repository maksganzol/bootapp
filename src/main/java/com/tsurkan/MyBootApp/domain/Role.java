package com.tsurkan.MyBootApp.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    TEACHER,
    STUDENT,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
