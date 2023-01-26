package com.bader.domain.IAM.ports;

import com.bader.domain.IAM.model.User;

import java.util.Optional;

public interface UserRepository {
    User findById(String username);

    Optional<User> createUser(User user);
}
