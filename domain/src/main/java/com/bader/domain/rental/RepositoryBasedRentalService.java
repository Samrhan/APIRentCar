package com.bader.domain.rental;

import com.bader.domain.rental.model.CartEntry;
import com.bader.domain.rental.model.Reservation;
import com.bader.domain.rental.repository.CartEntryRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryBasedRentalService implements RentalService {

    private final CartEntryRepository cartEntryRepository;

    public RepositoryBasedRentalService(CartEntryRepository cartEntryRepository) {
        this.cartEntryRepository = cartEntryRepository;
    }

    @Override
    public List<CartEntry> getCart() {
        return this.cartEntryRepository.getCart();
    }

    @Override
    public boolean deleteCart() {
        return this.cartEntryRepository.deleteCart();
    }

    @Override
    public Optional<CartEntry> addCartEntry(UUID carId, Date startDate, Date endDate) {
        if(isCarAvailableBetween(carId, startDate, endDate)){
            return Optional.ofNullable(this.cartEntryRepository.addCartEntry(carId, startDate, endDate));
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteCartEntry(UUID cartEntryId) {
        return this.cartEntryRepository.deleteCartEntry(cartEntryId);
    }

    private boolean isCarAvailableBetween(UUID carId, Date startDate, Date endDate) {
        List<Reservation> carReservations = this.cartEntryRepository.getReservationsBetween(carId, startDate, endDate);
        return carReservations.size() == 0;
    }
}
