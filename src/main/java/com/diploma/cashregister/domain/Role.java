package com.diploma.cashregister.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    EMPLOYEE,ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
