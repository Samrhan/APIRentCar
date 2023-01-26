package com.bader.infrastructure.persitence.rental;

import com.bader.domain.rental.model.Reservation;
import com.bader.domain.rental.repository.ReservationRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JPABasedReservationRepository implements ReservationRepository {
    private final JPAReservationRepository jpaReservationRepository;

    public JPABasedReservationRepository(JPAReservationRepository jpaReservationRepository) {
        this.jpaReservationRepository = jpaReservationRepository;
    }

    @Override
    public List<Reservation> getReservationsBetweenForCar(UUID carId, Date startDate, Date endDate) {
        return new ArrayList<>();
    }

    @Override
    public List<Reservation> getFutureReservationsForCar(UUID carId) {
        return new ArrayList<>();
    }
}
