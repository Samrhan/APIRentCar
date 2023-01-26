package com.bader.ports.web.auth.dto;

import com.bader.domain.user.model.Customer;

import java.util.UUID;

public class CustomerIdResponse {
    private final UUID id;

    public CustomerIdResponse(Customer customer) {
        this.id = customer.getId();
    }

    public UUID getId() {
        return id;
    }
}
