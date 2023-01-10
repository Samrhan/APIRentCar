package com.bader.infrastructure.persitence.rental;

import com.bader.domain.rental.model.CartEntry;
import com.bader.domain.rental.repository.RentalRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JPABasedRentalRepository implements RentalRepository {
    private final JPARentalRepository jpaRentalRepository;

    public JPABasedRentalRepository(JPARentalRepository jpaRentalRepository) {
        this.jpaRentalRepository = jpaRentalRepository;
    }

    @Override
    public List<CartEntry> getCart() {
        return new ArrayList<>();
    }
}
