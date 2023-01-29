package com.ssa.controllers.http.IAM.dto;

import com.ssa.domain.IAM.model.Customer;

import java.util.UUID;

public class CustomerDetailResponse {
    private final UUID id;
    private final String firstName;
    private final String lastName;

    public CustomerDetailResponse(Customer customer) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
