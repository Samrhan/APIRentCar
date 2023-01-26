package com.bader.domain.user.repository;

import com.bader.domain.user.model.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Optional<Customer> createCustomer(String firstName, String lastName, String associatedUserUsername);

    Optional<Customer> getCustomer(UUID id);

    Optional<Customer> getCustomerByEmail(String email);
}
