package com.bader.domain.catalog;

import com.bader.domain.catalog.model.Car;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CatalogService {
    List<Car> getCatalog();

    Car addCar(String model, String brand, String color, Integer year, BigDecimal price);

    Optional<Car> getCar(UUID id);

    Optional<Car> updateCar(UUID id, String model, String brand, String color, Integer year, BigDecimal price);
}
