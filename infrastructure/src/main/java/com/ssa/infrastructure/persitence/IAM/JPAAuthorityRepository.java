package com.ssa.infrastructure.persitence.IAM;

import com.ssa.infrastructure.persitence.IAM.entity.AuthorityEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface JPAAuthorityRepository extends CrudRepository<AuthorityEntity, UUID> {
}
