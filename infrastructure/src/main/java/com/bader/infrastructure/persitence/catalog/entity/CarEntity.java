package com.bader.infrastructure.persitence.catalog.entity;

import com.bader.domain.catalog.model.Car;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.PersistenceCreator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "CarEntity")
public class CarEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(name = "MODEL", nullable = false)
    private String model;

    @Column(name = "BRAND", nullable = false)
    private String brand;

    @Column(name = "COLOR", nullable = false)
    private String color;

    @Column(name = "RELEASE_YEAR", nullable = false)
    private Integer year;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @PersistenceCreator
    public CarEntity(String model, String brand, String color, Integer year, BigDecimal price) {
        this.model = model;
        this.brand = brand;
        this.color = color;
        this.year = year;
        this.price = price;
    }

    protected CarEntity() {
    }

    public CarEntity(UUID id, String model, String brand, String color, Integer year, BigDecimal price) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.color = color;
        this.year = year;
        this.price = price;
    }

    public Car toModel() {
        return new Car(id, model, brand, color, year, price);
    }

    public UUID getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public Integer getYear() {
        return year;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
