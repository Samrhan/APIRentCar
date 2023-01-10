package com.bader.infrastructure.persitence.IAM;

import com.bader.infrastructure.persitence.IAM.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface JPACustomerRepository extends CrudRepository<CustomerEntity, UUID> {
}
