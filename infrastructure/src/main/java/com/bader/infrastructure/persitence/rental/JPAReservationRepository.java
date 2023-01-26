package com.bader.infrastructure.persitence.rental;

import com.bader.domain.catalog.model.Car;
import com.bader.domain.user.model.Customer;
import com.bader.infrastructure.persitence.rental.entity.ReservationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.UUID;

public interface JPAReservationRepository extends CrudRepository<ReservationEntity, UUID> {
}
