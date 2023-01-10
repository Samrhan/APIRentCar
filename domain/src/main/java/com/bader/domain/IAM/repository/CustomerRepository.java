package com.bader.domain.IAM.repository;

import com.bader.domain.IAM.model.Customer;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> createCustomer(String firstName, String lastName, String email, String phone);
}
