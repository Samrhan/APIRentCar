package com.bader.domain.rental.model;

import com.bader.domain.IAM.model.Customer;
import com.bader.domain.catalog.model.Car;

import java.math.BigDecimal;
import java.util.Date;

public class CartEntry {
    private Customer customer;
    private Car car;
    private Date startDate;
    private Date endDate;

    public CartEntry(Customer customer, Car car, Date startDate, Date endDate) {
        this.customer = customer;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
