package com.bader.infrastructure.persitence.IAM.service;

import com.bader.domain.IAM.RepositoryBasedIAMService;
import com.bader.domain.IAM.repository.CustomerRepository;
import com.bader.domain.IAM.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration()
public class IAMServiceConfiguration {
    @Bean()
    public RepositoryBasedIAMService repositoryBasedIAMService(UserRepository userRepository, CustomerRepository customerRepository) {
        return new RepositoryBasedIAMService(userRepository, customerRepository);
    }
}
