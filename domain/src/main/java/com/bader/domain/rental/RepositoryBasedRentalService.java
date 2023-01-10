package com.bader.domain.rental;

import com.bader.domain.rental.model.CartEntry;
import com.bader.domain.rental.repository.CartEntryRepository;

import java.util.List;

public class RepositoryBasedRentalService implements RentalService {

    private final CartEntryRepository cartEntryRepository;

    public RepositoryBasedRentalService(CartEntryRepository cartEntryRepository) {
        this.cartEntryRepository = cartEntryRepository;
    }

    @Override
    public List<CartEntry> getCart() {
        return this.cartEntryRepository.getCart();
    }
}
