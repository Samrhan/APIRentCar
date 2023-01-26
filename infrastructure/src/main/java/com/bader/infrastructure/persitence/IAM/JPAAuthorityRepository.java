package com.bader.infrastructure.persitence.IAM;

import com.bader.infrastructure.persitence.IAM.entity.AuthorityEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface JPAAuthorityRepository extends CrudRepository<AuthorityEntity, UUID> {
}
