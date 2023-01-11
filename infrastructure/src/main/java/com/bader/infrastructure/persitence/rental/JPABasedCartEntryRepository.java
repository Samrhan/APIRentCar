package com.bader.infrastructure.persitence.rental;

import com.bader.domain.rental.model.CartEntry;
import com.bader.domain.rental.model.Reservation;
import com.bader.domain.rental.repository.CartEntryRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JPABasedCartEntryRepository implements CartEntryRepository {
    private final JPACartEntryRepository jpaCartEntryRepository;
    private final JPAReservationRepository jpaReservationRepository;

    public JPABasedCartEntryRepository(JPACartEntryRepository jpaCartEntryRepository, JPAReservationRepository jpaReservationRepository) {
        this.jpaCartEntryRepository = jpaCartEntryRepository;
        this.jpaReservationRepository = jpaReservationRepository;
    }

    @Override
    public List<CartEntry> getCart() {
        return new ArrayList<>();
    }

    @Override
    public CartEntry addCartEntry(UUID carId, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public boolean deleteCart() {
        return false;
    }

    @Override
    public boolean deleteCartEntry(UUID cartEntryId) {
        return false;
    }

    @Override
    public List<Reservation> getReservationsBetween(UUID carId, Date startDate, Date endDate) {
        return new ArrayList<>();
    }
}
