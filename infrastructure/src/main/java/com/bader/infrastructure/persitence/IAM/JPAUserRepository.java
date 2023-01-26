package com.bader.infrastructure.persitence.IAM;

import com.bader.infrastructure.persitence.IAM.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface JPAUserRepository extends CrudRepository<UserEntity, String> {
}
