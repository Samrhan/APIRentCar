package com.bader.domain.user.repository;

import com.bader.domain.user.model.User;

import java.util.Optional;

public interface UserRepository {
    User findById(String username);

    Optional<User> createUser(User user);
}
