package com.bader.infrastructure.persitence.user;

import com.bader.infrastructure.persitence.user.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface JPAUserRepository extends CrudRepository<UserEntity, String> {
}
