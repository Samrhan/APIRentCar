package com.bader.infrastructure.persitence.user;

import com.bader.domain.user.model.Customer;
import com.bader.domain.user.repository.CustomerRepository;
import com.bader.infrastructure.persitence.user.entity.CustomerEntity;
import com.bader.infrastructure.persitence.user.entity.UserEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JPABasedCustomerRepository implements CustomerRepository {
    private final JPACustomerRepository jpaCustomerRepository;
    private final JPAUserRepository jpaUserRepository;

    public JPABasedCustomerRepository(JPACustomerRepository jpaCustomerRepository, JPAUserRepository jpaUserRepository) {
        this.jpaCustomerRepository = jpaCustomerRepository;
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Optional<Customer> createCustomer(String firstName, String lastName, String associatedUserUsername) {
        try {
            Optional<UserEntity> associatedUser = jpaUserRepository.findById(associatedUserUsername);
            if (associatedUser.isPresent()){
                CustomerEntity newCustomer = new CustomerEntity(firstName, lastName);
                newCustomer.setAssociatedUser(associatedUser.get());

                return Optional.of(jpaCustomerRepository.save(newCustomer).toModel());
            } else {
                return Optional.empty();
            }
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
