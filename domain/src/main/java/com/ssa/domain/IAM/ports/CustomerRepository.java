package com.ssa.domain.IAM.ports;

import com.ssa.domain.IAM.model.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Optional<Customer> createCustomer(String firstName, String lastName, String associatedUserUsername);

    Optional<Customer> getCustomer(UUID id);

    Optional<Customer> getCustomerByEmail(String email);
}
