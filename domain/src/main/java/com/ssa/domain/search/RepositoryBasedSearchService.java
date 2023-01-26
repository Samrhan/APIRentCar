package com.ssa.domain.search;

import com.ssa.domain.catalog.model.Car;
import com.ssa.domain.catalog.ports.CatalogRepository;

import java.math.BigDecimal;
import java.util.List;

public class RepositoryBasedSearchService implements SearchService {
    private final CatalogRepository catalogRepository;

    public RepositoryBasedSearchService(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    @Override
    public List<Car> searchCar(String model, String brand, String color, Integer year, BigDecimal priceMin, BigDecimal priceMax) {
        return catalogRepository.searchCar(model, brand, color, year, priceMin, priceMax);
    }
}
