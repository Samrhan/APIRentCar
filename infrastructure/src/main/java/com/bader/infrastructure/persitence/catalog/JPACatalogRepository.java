package com.bader.infrastructure.persitence.catalog;

import com.bader.infrastructure.persitence.catalog.entity.CarEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface JPACatalogRepository extends CrudRepository<CarEntity, UUID> {
}
