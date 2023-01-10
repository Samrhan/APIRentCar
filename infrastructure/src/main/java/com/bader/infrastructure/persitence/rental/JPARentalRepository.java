package com.bader.infrastructure.persitence.rental;

import com.bader.infrastructure.persitence.rental.entity.CartEntryEntity;
import org.springframework.data.repository.CrudRepository;

public interface JPARentalRepository extends CrudRepository<CartEntryEntity, Integer> {

}
