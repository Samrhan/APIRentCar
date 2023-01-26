package com.bader.infrastructure.persitence.search.configuration;

import com.bader.domain.catalog.ports.CatalogRepository;
import com.bader.domain.search.RepositoryBasedSearchService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchServiceConfiguration {
    @Bean
    public RepositoryBasedSearchService repositoryBasedSearchService(CatalogRepository catalogRepository) {
        return new RepositoryBasedSearchService(catalogRepository);
    }
}
