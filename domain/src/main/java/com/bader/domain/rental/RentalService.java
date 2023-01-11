package com.bader.domain.rental;

import com.bader.domain.rental.model.CartEntry;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface RentalService {
    List<CartEntry> getCart();

    boolean deleteCart();

    CartEntry addCartEntry(UUID carId, Date startDate, Date endDate);

    boolean deleteCartEntry(UUID cartEntryId);
}
