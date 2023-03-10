package com.ssa.domain.catalog;

import com.ssa.domain.catalog.model.Car;
import com.ssa.domain.catalog.ports.CatalogRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public Optional<Car> getCar(UUID id) {
        return this.catalogRepository.getCar(id);
    }

    @Override
    public Optional<Car> updateCar(UUID id, String model, String brand, String color, Integer year, BigDecimal price) {
        return this.catalogRepository
                .getCar(id)
                .map(car -> catalogRepository.save(id, model, brand, color, year, price));
    }

    @Override
    public boolean deleteCar(UUID id) {
        return catalogRepository.deleteCar(id);
    }

    @Override
    public Car addCar(String model, String brand, String color, Integer year, BigDecimal price) {
        return this.catalogRepository.addCar(model, brand, color, year, price);
    }
}
