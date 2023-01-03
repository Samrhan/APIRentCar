package com.bader.domain.catalog;

import com.bader.domain.catalog.model.Car;
import com.bader.domain.catalog.repository.CatalogRepository;

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
    public Optional<Car> getCar(UUID id){
        return this.catalogRepository.getCar(id);
    }

    @Override
    public Optional<Car> updateCar(UUID id, String model, String brand, String color, Integer year, BigDecimal price) {
        return this.catalogRepository
                .getCar(id)
                .map(car -> catalogRepository.save(id, model, brand, color, year, price));
    }

    @Override
    public Optional<Boolean> deleteCar(UUID id) {
        Integer deletedCount = catalogRepository.deleteCar(id);
        return deletedCount > 0 ? Optional.of(true) : Optional.empty();
    }

    @Override
    public Car addCar(String model, String brand, String color, Integer year, BigDecimal price) {
        return this.catalogRepository.addCar(model, brand, color, year, price);
    }
}
