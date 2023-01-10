package com.bader.domain.IAM;

import com.bader.domain.IAM.model.Customer;

import java.util.Optional;
import java.util.UUID;

public interface IAMService {
    Optional<Customer> createCustomer(String firstName, String lastName, String email, String phone);

    Optional<Customer> getCustmer(UUID id);
}
