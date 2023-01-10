package com.bader.domain.rental;

import com.bader.domain.rental.model.CartEntry;

import java.util.List;

public interface RentalService {
    List<CartEntry> getCart();
}
