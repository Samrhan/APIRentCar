package com.bader.domain.user;

import com.bader.domain.user.model.Customer;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    void registerCustomer(String username, String password, String role, String firstName, String lastName);

    void registerSeller(String username, String password, String role);

    Optional<Customer> getCustomer(UUID id);

    Optional<Customer> getCustomerByEmail(String email);
}
