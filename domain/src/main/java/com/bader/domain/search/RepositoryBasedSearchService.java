package com.bader.domain.search;

import com.bader.domain.catalog.repository.CatalogRepository;

public class RepositoryBasedSearchService implements SearchService {
    private final CatalogRepository catalogRepository;

    public RepositoryBasedSearchService(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }
}
