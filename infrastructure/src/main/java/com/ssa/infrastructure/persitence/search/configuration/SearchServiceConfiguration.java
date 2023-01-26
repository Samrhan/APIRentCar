package com.ssa.infrastructure.persitence.search.configuration;

import com.ssa.domain.catalog.ports.CatalogRepository;
import com.ssa.domain.search.RepositoryBasedSearchService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchServiceConfiguration {
    @Bean
    public RepositoryBasedSearchService repositoryBasedSearchService(CatalogRepository catalogRepository) {
        return new RepositoryBasedSearchService(catalogRepository);
    }
}
