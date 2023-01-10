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
    public Optional<Customer> createCustomer(String firstName, String lastName, String email, String phone) {
        return this.customerRepository.createCustomer(firstName, lastName, email, phone);
    }

    @Override
    public Optional<Customer> getCustmer(UUID id) {
        return this.customerRepository.getCustomer(id);
    }
}
