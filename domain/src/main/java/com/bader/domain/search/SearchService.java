package com.bader.domain.search;

import com.bader.domain.catalog.model.Car;

import java.math.BigDecimal;
import java.util.List;

public interface SearchService {
    List<Car> searchCar(String model, String brand, String color, Integer year, BigDecimal priceMin, BigDecimal priceMax);
}
