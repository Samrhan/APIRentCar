package com.bader.ports.web.IAM.dto.response;

import com.bader.domain.IAM.model.Customer;

import java.util.UUID;

public class CustomerDetailResponse {
    private final UUID id;
    private final String firstName;
    private final String lastName;
    private final String phone;
    private final String email;

    public CustomerDetailResponse(Customer customer) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.phone = customer.getPhone();
        this.email = customer.getEmail();
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

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
