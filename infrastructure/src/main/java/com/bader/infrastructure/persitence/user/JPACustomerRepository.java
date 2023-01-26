package com.bader.infrastructure.persitence.user;

import com.bader.infrastructure.persitence.user.entity.CustomerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface JPACustomerRepository extends CrudRepository<CustomerEntity, UUID> {
    @Query("SELECT customer FROM CustomerEntity customer WHERE customer.associatedUser.username = :email")
    Optional<CustomerEntity> findByEmail(@Param("email") String email);
}
