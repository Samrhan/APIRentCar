package com.bader.ports.web.IAM.dto.response;

import com.bader.domain.IAM.model.Customer;

import java.util.UUID;

public class CustomerResponse {
    private final UUID id;

    public CustomerResponse(Customer customer) {
        this.id = customer.getId();
    }

    public UUID getId() {
        return id;
    }
}
