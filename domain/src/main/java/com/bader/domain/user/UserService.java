package com.bader.domain.user;

import com.bader.domain.user.model.User;

public interface UserService {
    void registerUser(String username, String password, String role);
}
