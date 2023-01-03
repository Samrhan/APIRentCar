package com.bader.ports.web.catalog.dto.response;

import com.bader.domain.catalog.model.Car;

import java.math.BigDecimal;
import java.util.UUID;

public class CarResponse {

    UUID id;
    String model;
    String brand;
    String color;
    Integer year;
    BigDecimal price;
    public CarResponse(Car car){
        this.id = car.getId();
        this.model = car.getModel();
        this.brand = car.getBrand();
        this.color = car.getColor();
        this.year = car.getYear();
        this.price = car.getPrice();
    }
}
