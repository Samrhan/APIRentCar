package com.bader.infrastructure.persitence.rental;

import com.bader.domain.rental.model.CartEntry;
import com.bader.domain.rental.repository.CartEntryRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JPABasedCartEntryRepository implements CartEntryRepository {
    private final JPACartEntryRepository jpaCartEntryRepository;

    public JPABasedCartEntryRepository(JPACartEntryRepository jpaCartEntryRepository) {
        this.jpaCartEntryRepository = jpaCartEntryRepository;
    }

    @Override
    public List<CartEntry> getCart() {
        return new ArrayList<>();
    }
}
