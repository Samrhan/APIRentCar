package com.bader.infrastructure.persitence.IAM;

import com.bader.domain.IAM.repository.CustomerRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JPABasedCustomerRepository implements CustomerRepository {
    private final JPACustomerRepository jpaCustomerRepository;

    public JPABasedCustomerRepository(JPACustomerRepository jpaCustomerRepository) {
        this.jpaCustomerRepository = jpaCustomerRepository;
    }
}
