package com.bader.infrastructure.persitence.user;

import com.bader.infrastructure.persitence.user.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface JPAUserRepository extends CrudRepository<UserEntity, String> {
}
