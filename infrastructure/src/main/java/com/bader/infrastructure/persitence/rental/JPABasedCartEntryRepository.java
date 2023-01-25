package com.bader.infrastructure.persitence.rental;

import com.bader.domain.rental.model.CartEntry;
import com.bader.domain.rental.repository.CartEntryRepository;
import com.bader.infrastructure.persitence.rental.entity.CartEntryEntity;
import com.bader.infrastructure.persitence.user.JPACustomerRepository;
import com.bader.infrastructure.persitence.user.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class JPABasedCartEntryRepository implements CartEntryRepository {
    private final JPACartEntryRepository jpaCartEntryRepository;
    private final JPACustomerRepository jpaCustomerRepository;

    public JPABasedCartEntryRepository(JPACartEntryRepository jpaCartEntryRepository, JPACustomerRepository jpaCustomerRepository) {
        this.jpaCartEntryRepository = jpaCartEntryRepository;
        this.jpaCustomerRepository = jpaCustomerRepository;
    }

    @Override
    public List<CartEntry> getCart(String associatedUserUsername) {
        Optional<CustomerEntity> customer = jpaCustomerRepository.findByEmail(associatedUserUsername);
        if (customer.isPresent()){
            Optional<List<CartEntryEntity>> cart = jpaCartEntryRepository.findAllByCustomer(customer.get());
            if (cart.isPresent()){
                return cart.get().stream().map(CartEntryEntity::toModel).collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }

    @Override
    public CartEntry addCartEntry(UUID carId, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public boolean deleteCart() {
        return false;
    }

    @Override
    public boolean deleteCartEntry(UUID cartEntryId) {
        return false;
    }
}
