package com.bader.infrastructure.persitence.rental;

import com.bader.infrastructure.persitence.rental.entity.CartEntryEntity;
import com.bader.infrastructure.persitence.user.entity.CustomerEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JPACartEntryRepository extends CrudRepository<CartEntryEntity, UUID> {
    @Query("SELECT cartEntry FROM CartEntryEntity cartEntry WHERE cartEntry.customer = :customer")
    Optional<List<CartEntryEntity>> findAllByCustomer(@Param("customer") CustomerEntity customer);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartEntryEntity cartEntry WHERE cartEntry.customer = :customer")
    void deleteAllByCustomer(@Param("customer") CustomerEntity customer);
}
