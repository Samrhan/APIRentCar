package com.ssa.adapters.persitence.IAM;

import com.ssa.adapters.persitence.IAM.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface JPAUserRepository extends CrudRepository<UserEntity, String> {
}
