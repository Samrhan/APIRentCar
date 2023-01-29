package com.ssa.adapters.persitence.IAM.configuration;

import com.ssa.domain.IAM.RepositoryBasedIAMService;
import com.ssa.domain.IAM.ports.CustomerRepository;
import com.ssa.domain.IAM.ports.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration()
public class IAMServiceConfiguration {
    @Bean()
    public RepositoryBasedIAMService repositoryBasedIAMService(UserRepository userRepository, CustomerRepository customerRepository) {
        return new RepositoryBasedIAMService(userRepository, customerRepository);
    }
}
