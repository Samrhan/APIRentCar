package com.bader.domain.IAM;

import com.bader.domain.IAM.model.Customer;
import com.bader.domain.IAM.repository.CustomerRepository;

import java.util.Optional;
import java.util.UUID;

public class RepositoryBasedIAMService implements IAMService {
    private final CustomerRepository customerRepository;

    public RepositoryBasedIAMService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Customer> createCustomer(String firstName, String lastName, String phone, String email) {
        return this.customerRepository.createCustomer(firstName, lastName, phone, email);
    }

    @Override
    public Optional<Customer> getCustomer(UUID id) {
        return this.customerRepository.getCustomer(id);
    }

    @Override
    public Optional<Customer> getCustomerByEmail(String email) {
        return this.customerRepository.getCustomerByEmail(email);
    }
}
