package com.bader.domain.rental;

import com.bader.domain.rental.model.CartEntry;
import com.bader.domain.rental.repository.RentalRepository;

import java.util.List;

public class RepositoryBasedRentalService implements RentalService {

    private final RentalRepository rentalRepository;

    public RepositoryBasedRentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Override
    public List<CartEntry> getCart() {
        return this.rentalRepository.getCart();
    }
}
