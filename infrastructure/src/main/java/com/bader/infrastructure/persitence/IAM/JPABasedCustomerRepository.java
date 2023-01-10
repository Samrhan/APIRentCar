package com.bader.infrastructure.persitence.IAM;

import com.bader.domain.IAM.model.Customer;
import com.bader.domain.IAM.repository.CustomerRepository;
import com.bader.infrastructure.persitence.IAM.entity.CustomerEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JPABasedCustomerRepository implements CustomerRepository {
    private final JPACustomerRepository jpaCustomerRepository;

    public JPABasedCustomerRepository(JPACustomerRepository jpaCustomerRepository) {
        this.jpaCustomerRepository = jpaCustomerRepository;
    }

    @Override
    public Optional<Customer> createCustomer(String firstName, String lastName, String phone, String email) {
        try {
            return Optional.of(jpaCustomerRepository.save(new CustomerEntity(firstName, lastName, phone, email)).toModel());
        } catch (DataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> getCustomer(UUID id) {
        return jpaCustomerRepository.findById(id).map(CustomerEntity::toModel);
    }

    @Override
    public Optional<Customer> getCustomerByEmail(String email) {
        return jpaCustomerRepository.findByEmail(email).map(CustomerEntity::toModel);
    }
}
