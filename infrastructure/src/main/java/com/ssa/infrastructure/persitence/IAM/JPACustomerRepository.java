package com.ssa.infrastructure.persitence.IAM;

import com.ssa.infrastructure.persitence.IAM.entity.CustomerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface JPACustomerRepository extends CrudRepository<CustomerEntity, UUID> {
    @Query("SELECT customer FROM CustomerEntity customer WHERE customer.associatedUser.username = :email")
    Optional<CustomerEntity> findByEmail(@Param("email") String email);
}
