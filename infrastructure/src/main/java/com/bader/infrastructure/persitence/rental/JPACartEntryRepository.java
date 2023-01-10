package com.bader.infrastructure.persitence.rental;

import com.bader.infrastructure.persitence.rental.entity.CartEntryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface JPACartEntryRepository extends CrudRepository<CartEntryEntity, UUID> {

}
