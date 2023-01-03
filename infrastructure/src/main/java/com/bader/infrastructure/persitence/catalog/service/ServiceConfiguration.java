package com.bader.infrastructure.persitence.catalog.service;

import com.bader.domain.catalog.RepositoryBasedCatalogService;
import com.bader.domain.catalog.repository.CatalogRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {
    @Bean
    public RepositoryBasedCatalogService repositoryBasedCatalogService(CatalogRepository catalogRepository) {
        return new RepositoryBasedCatalogService(catalogRepository);
    }
}
