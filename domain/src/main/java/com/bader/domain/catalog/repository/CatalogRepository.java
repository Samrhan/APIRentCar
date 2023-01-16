package com.bader.domain.catalog.repository;

import com.bader.domain.catalog.model.Car;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CatalogRepository {
    List<Car> getCatalog();

    Car addCar(String model, String brand, String color, Integer year, BigDecimal price);

    Optional<Car> getCar(UUID id);

    Car save(UUID id, String model, String brand, String color, Integer year, BigDecimal price);

    boolean deleteCar(UUID id);

    List<Car> searchCar(String model, String brand, String color, Integer year, BigDecimal priceMin, BigDecimal priceMax);
}
