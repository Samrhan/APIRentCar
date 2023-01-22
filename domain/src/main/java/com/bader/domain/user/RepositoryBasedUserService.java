package com.bader.domain.user;

import com.bader.domain.user.model.Authority;
import com.bader.domain.user.model.User;
import com.bader.domain.user.repository.UserRepository;

import java.util.List;

public class RepositoryBasedUserService implements UserService {
    private final UserRepository userRepository;


    public RepositoryBasedUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(String username, String password, String role) {
        List<Authority> authorities = List.of(new Authority(role));
        userRepository.save(new User(username, password, true, authorities));
    }
}
