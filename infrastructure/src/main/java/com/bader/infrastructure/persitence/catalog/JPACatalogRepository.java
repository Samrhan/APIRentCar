package com.bader.infrastructure.persitence.catalog;

import com.bader.infrastructure.persitence.catalog.entity.CarEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface JPACatalogRepository extends CrudRepository<CarEntity, Integer> {
    @Query("SELECT car FROM CarEntity car WHERE car.id = :id")
    Optional<CarEntity> findById(@Param("id") UUID id);
}
