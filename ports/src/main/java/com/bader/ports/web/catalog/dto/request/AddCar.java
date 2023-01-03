package com.bader.ports.web.catalog.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class AddCar {
    @NotBlank
    private String model;

    @NotBlank
    private String brand;

    @NotBlank
    private String color;

    @NotBlank
    private Integer year;

    @NotBlank
    private BigDecimal price;

    @JsonCreator
    public AddCar(@JsonProperty("model") String model, @JsonProperty("brand") String brand, @JsonProperty("color") String color, @JsonProperty("year") Integer year, @JsonProperty("price") BigDecimal price) {
        this.model = model;
        this.brand = brand;
        this.color = color;
        this.year = year;
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
