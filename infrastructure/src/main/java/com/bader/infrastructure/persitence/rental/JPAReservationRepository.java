package com.bader.infrastructure.persitence.rental;

import com.bader.infrastructure.persitence.rental.entity.ReservationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface JPAReservationRepository extends CrudRepository<ReservationEntity, UUID> {

}
