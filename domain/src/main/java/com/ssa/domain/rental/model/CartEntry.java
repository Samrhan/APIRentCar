package com.ssa.domain.rental.model;

import com.ssa.domain.IAM.model.Customer;
import com.ssa.domain.catalog.model.Car;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CartEntry {

    private final UUID id;
    private final Customer customer;
    private final Car car;
    private final Date startDate;
    private final Date endDate;

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

    public int getReservationDurationInDays() {
        // Code from https://stackabuse.com/how-to-get-the-number-of-days-between-dates-in-java/
        long dateBeforeInMs = startDate.getTime();
        long dateAfterInMs = endDate.getTime();
        long timeDiff = Math.abs(dateAfterInMs - dateBeforeInMs);
        long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
        daysDiff++; // Count 1 more day as the boundaries are inclusive (i.e, renting from Jan. 6 to Jan. 10 is five days and not just (10 - 6 = 4) days)

        return (int) daysDiff;
    }
}
