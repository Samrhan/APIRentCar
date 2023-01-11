package com.bader.domain.search;

import com.bader.domain.search.repository.SearchRepository;

public class RepositoryBasedSearchService implements SearchService {
    private final SearchRepository searchRepository;

    public RepositoryBasedSearchService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }
}
