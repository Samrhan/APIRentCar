package com.ssa.adapters.persitence.rental;

import com.ssa.adapters.persitence.IAM.entity.CustomerEntity;
import com.ssa.adapters.persitence.rental.entity.CartEntryEntity;
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

    @Modifying
    @Transactional
    @Query("DELETE FROM CartEntryEntity cartEntry WHERE cartEntry.id = :cartEntryId and cartEntry.customer = :customer")
    void deleteByIdIfOwned(@Param("cartEntryId") UUID cartEntryId, @Param("customer") CustomerEntity customer);
}
