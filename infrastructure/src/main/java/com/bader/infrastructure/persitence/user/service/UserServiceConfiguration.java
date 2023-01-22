package com.bader.infrastructure.persitence.user.service;

import com.bader.domain.user.RepositoryBasedUserService;
import com.bader.domain.user.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration()
public class UserServiceConfiguration {
    @Bean()
    public RepositoryBasedUserService repositoryBasedUserService(UserRepository userRepository) {
        return new RepositoryBasedUserService(userRepository);
    }
}
