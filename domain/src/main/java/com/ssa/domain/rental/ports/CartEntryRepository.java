package com.ssa.domain.rental.ports;

import com.ssa.domain.rental.model.CartEntry;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface CartEntryRepository {
    List<CartEntry> getCart(String associatedUserUsername);

    CartEntry addCartEntry(String associatedUserUsername, UUID carId, Date startDate, Date endDate);

    boolean deleteCart(String associatedUserUsername);

    boolean deleteCartEntry(String associatedUserUsername, UUID cartEntryId);
}
