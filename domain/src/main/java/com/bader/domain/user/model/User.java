package com.bader.domain.user.model;

import java.util.List;

public class User {
    private final String username;
    private final String password;
    private final boolean enabled;
    private final List<Authority> authorities;

    public User(String username, String password, boolean enabled, List<Authority> authorities) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }
}
