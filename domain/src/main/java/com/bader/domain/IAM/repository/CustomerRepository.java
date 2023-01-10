package com.bader.domain.IAM.repository;

import com.bader.domain.IAM.model.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Optional<Customer> createCustomer(String firstName, String lastName, String email, String phone);

    Optional<Customer> getCustomer(UUID id);
}
