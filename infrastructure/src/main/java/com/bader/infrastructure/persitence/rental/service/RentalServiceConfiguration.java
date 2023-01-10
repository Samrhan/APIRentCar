package com.bader.infrastructure.persitence.rental.service;

import com.bader.domain.rental.RepositoryBasedRentalService;
import com.bader.domain.rental.repository.RentalRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RentalServiceConfiguration {
    @Bean
    public RepositoryBasedRentalService repositoryBasedRentalService(RentalRepository rentalRepository){
        return new RepositoryBasedRentalService(rentalRepository);
    }
}
