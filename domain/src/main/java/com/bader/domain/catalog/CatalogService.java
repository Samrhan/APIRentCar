package com.bader.domain.catalog;

import com.bader.domain.catalog.model.Car;

import java.math.BigDecimal;
import java.util.List;

public interface CatalogService {
    List<Car> getCatalog();

    Car addCar(String model, String brand, String color, Integer year, BigDecimal price);
}
