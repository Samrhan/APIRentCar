package com.bader.infrastructure.persitence.rental;

import com.bader.infrastructure.persitence.rental.entity.CartEntryEntity;
import org.springframework.data.repository.CrudRepository;

public interface JPACartEntryRepository extends CrudRepository<CartEntryEntity, Integer> {

}
