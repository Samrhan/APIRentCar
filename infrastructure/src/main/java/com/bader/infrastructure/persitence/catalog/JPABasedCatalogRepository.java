package com.bader.infrastructure.persitence.catalog;

import com.bader.domain.catalog.model.Car;
import com.bader.domain.catalog.repository.CatalogRepository;
import com.bader.infrastructure.persitence.catalog.entity.CarEntity;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class JPABasedCatalogRepository implements CatalogRepository {

    private final JPACatalogRepository jpaCatalogRepository;

    public JPABasedCatalogRepository(JPACatalogRepository jpaCatalogRepository) {
        this.jpaCatalogRepository = jpaCatalogRepository;
    }

    @Override
    public List<Car> getCatalog() {
        return StreamSupport
                .stream(jpaCatalogRepository.findAll().spliterator(), false)
                .map(CarEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Car> getCar(UUID id) {
        System.out.println(jpaCatalogRepository.findById(id));
        return jpaCatalogRepository.findById(id).map(CarEntity::toModel);
    }

    @Override
    public Car save(UUID id, String model, String brand, String color, Integer year, BigDecimal price) {
        return jpaCatalogRepository.save(new CarEntity(id, model, brand, color, year, price)).toModel();
    }


    @Override
    public Car addCar(String model, String brand, String color, Integer year, BigDecimal price) {
        return jpaCatalogRepository.save(new CarEntity(model, brand, color, year, price)).toModel();
    }
}
