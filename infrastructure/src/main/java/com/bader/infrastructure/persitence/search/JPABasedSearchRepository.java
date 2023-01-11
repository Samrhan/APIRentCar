package com.bader.infrastructure.persitence.search;

import com.bader.domain.search.repository.SearchRepository;
import com.bader.infrastructure.persitence.catalog.JPACatalogRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JPABasedSearchRepository implements SearchRepository {
    private final JPACatalogRepository jpaCatalogRepository;

    public JPABasedSearchRepository(JPACatalogRepository jpaCatalogRepository) {
        this.jpaCatalogRepository = jpaCatalogRepository;
    }
}
