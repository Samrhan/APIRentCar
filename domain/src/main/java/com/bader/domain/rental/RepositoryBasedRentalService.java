package com.bader.domain.rental;

import com.bader.domain.rental.model.CartEntry;
import com.bader.domain.rental.repository.CartEntryRepository;

import java.util.Date;
import java.util.List;
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
        return false;
    }

    @Override
    public CartEntry addCartEntry(UUID carId, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public boolean deleteCartEntry(UUID cartEntryId) {
        return false;
    }
}
