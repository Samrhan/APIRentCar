package com.bader.infrastructure.persitence.user;

import com.bader.infrastructure.persitence.user.entity.AuthorityEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface JPAAuthorityRepository extends CrudRepository<AuthorityEntity, UUID> {
}
