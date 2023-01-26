package com.bader.domain.catalog.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Car {

    private UUID id;
    private String model;
    private String brand;
    private String color;
    private Integer year;
    private BigDecimal price;

    public Car(UUID id, String model, String brand, String color, Integer year, BigDecimal price) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.color = color;
        this.year = year;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public String getModel() {
        return model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getYear() {
        return year;
    }
}
