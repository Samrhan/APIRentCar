package com.ssa.adapters.persitence.catalog;

import com.ssa.adapters.persitence.catalog.entity.CarEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.UUID;

public interface JPACatalogRepository extends CrudRepository<CarEntity, UUID> {

    @Query("SELECT car FROM CarEntity car WHERE" +
            "(:model is null or car.model = :model) and " +
            "(:brand is null or car.brand = :brand) and " +
            "(:color is null or car.color = :color) and " +
            "(:year is null or car.year = :year) and " +
            "(:priceMin is null or car.price >= :priceMin) and " +
            "(:priceMax is null or car.price <= :priceMax)"
    )
    Iterable<CarEntity> findAllByOptionalEntityFields(@Param("model") String model, @Param("brand") String brand, @Param("color") String color, @Param("year") Integer year, @Param("priceMin") BigDecimal priceMin, @Param("priceMax") BigDecimal priceMax);
}
