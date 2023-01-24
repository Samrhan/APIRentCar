package com.bader.domain.rental.model;

import com.bader.domain.user.model.Customer;
import com.bader.domain.catalog.model.Car;

import java.util.Date;
import java.util.UUID;

public class CartEntry {

    private UUID id;
    private Customer customer;
    private Car car;
    private Date startDate;
    private Date endDate;

    public CartEntry(UUID id, Customer customer, Car car, Date startDate, Date endDate) {
        this.id = id;
        this.customer = customer;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public UUID getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Car getCar() {
        return car;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
