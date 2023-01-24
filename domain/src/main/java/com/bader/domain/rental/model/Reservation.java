package com.bader.domain.rental.model;

import com.bader.domain.user.model.Customer;
import com.bader.domain.catalog.model.Car;

import java.util.Date;
import java.util.UUID;

public class Reservation {
    private final UUID id;
    private final Customer customer;
    private final Car car;
    private final Date startDate;
    private final Date endDate;
    private final Boolean paid;

    public Reservation(UUID id, Customer customer, Car car, Date startDate, Date endDate, Boolean paid) {
        this.id = id;
        this.customer = customer;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paid = paid;
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

    public Boolean getPaid() {
        return paid;
    }
}
