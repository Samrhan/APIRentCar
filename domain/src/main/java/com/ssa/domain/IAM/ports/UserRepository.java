package com.ssa.domain.IAM.ports;

import com.ssa.domain.IAM.model.User;

import java.util.Optional;

public interface UserRepository {
    User findById(String username);

    Optional<User> createUser(User user);
}
