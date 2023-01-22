package com.bader.infrastructure.persitence.user;

import com.bader.domain.user.model.User;
import com.bader.infrastructure.persitence.user.entity.AuthorityEntity;
import com.bader.infrastructure.persitence.user.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface JPAAuthorityRepository extends CrudRepository<AuthorityEntity, UUID> {
}
