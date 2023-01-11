package com.bader.infrastructure.persitence.search.service;

import com.bader.domain.search.RepositoryBasedSearchService;
import com.bader.domain.search.repository.SearchRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchServiceConfiguration {
    @Bean
    public RepositoryBasedSearchService repositoryBasedSearchService(SearchRepository searchRepository){
        return new RepositoryBasedSearchService(searchRepository);
    }
}
