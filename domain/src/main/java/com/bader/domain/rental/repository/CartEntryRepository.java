package com.bader.domain.rental.repository;

import com.bader.domain.rental.model.CartEntry;

import java.util.List;

public interface CartEntryRepository {
    List<CartEntry> getCart();
}
