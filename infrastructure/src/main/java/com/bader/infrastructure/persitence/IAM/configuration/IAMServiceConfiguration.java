package com.bader.infrastructure.persitence.IAM.configuration;

import com.bader.domain.IAM.RepositoryBasedIAMService;
import com.bader.domain.IAM.ports.CustomerRepository;
import com.bader.domain.IAM.ports.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration()
public class IAMServiceConfiguration {
    @Bean()
    public RepositoryBasedIAMService repositoryBasedIAMService(UserRepository userRepository, CustomerRepository customerRepository) {
        return new RepositoryBasedIAMService(userRepository, customerRepository);
    }
}
