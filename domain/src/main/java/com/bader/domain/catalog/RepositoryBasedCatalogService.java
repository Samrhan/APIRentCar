package com.bader.domain.catalog;

import com.bader.domain.catalog.model.Car;
import com.bader.domain.catalog.repository.CatalogRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
public class RepositoryBasedCatalogService implements CatalogService {

    private final CatalogRepository catalogRepository;

    public RepositoryBasedCatalogService(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    @Override
    public List<Car> getCatalog() {
        return this.catalogRepository.getCatalog();
    }

    @Override
    public Car addCar(String model, String brand, String color, Integer year, BigDecimal price) {
        return this.catalogRepository.addCar(model, brand, color, year, price);
    }
}
