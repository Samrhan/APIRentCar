package com.ssa.adapters.persitence.catalog.configuration;

import com.ssa.domain.catalog.RepositoryBasedCatalogService;
import com.ssa.domain.catalog.ports.CatalogRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CatalogServiceConfiguration {
    @Bean
    public RepositoryBasedCatalogService repositoryBasedCatalogService(CatalogRepository catalogRepository) {
        return new RepositoryBasedCatalogService(catalogRepository);
    }
}
