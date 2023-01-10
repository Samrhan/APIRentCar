package com.bader.domain.IAM;

import com.bader.domain.IAM.repository.CustomerRepository;

public class RepositoryBasedIAMService implements IAMService {
    private final CustomerRepository customerRepository;

    public RepositoryBasedIAMService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
}
