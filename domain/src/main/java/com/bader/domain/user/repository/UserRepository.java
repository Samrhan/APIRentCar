package com.bader.domain.user.repository;

import com.bader.domain.user.model.User;

public interface UserRepository {
    User save(User user);

    User findById(String username);
}
