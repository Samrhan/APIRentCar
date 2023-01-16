package com.bader.infrastructure.persitence.search.service;

import com.bader.domain.catalog.repository.CatalogRepository;
import com.bader.domain.search.RepositoryBasedSearchService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchServiceConfiguration {
    @Bean
    public RepositoryBasedSearchService repositoryBasedSearchService(CatalogRepository catalogRepository){
        return new RepositoryBasedSearchService(catalogRepository);
    }
}
