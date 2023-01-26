package com.bader.domain.IAM;

import com.bader.domain.IAM.model.Customer;

import java.util.Optional;
import java.util.UUID;

public interface IAMService {
    boolean registerCustomer(String username, String password, String role, String firstName, String lastName);

    boolean registerSeller(String username, String password, String role);

    Optional<Customer> getCustomer(UUID id);

    Optional<Customer> getCustomerByEmail(String email);
}
