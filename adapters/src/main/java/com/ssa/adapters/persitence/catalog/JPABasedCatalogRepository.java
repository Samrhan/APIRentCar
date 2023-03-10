package com.ssa.adapters.persitence.catalog;

import com.ssa.adapters.persitence.catalog.entity.CarEntity;
import com.ssa.domain.catalog.model.Car;
import com.ssa.domain.catalog.ports.CatalogRepository;
import org.springframework.dao.EmptyResultDataAccessException;
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
        return jpaCatalogRepository.findById(id).map(CarEntity::toModel);
    }

    @Override
    public Car save(UUID id, String model, String brand, String color, Integer year, BigDecimal price) {
        return jpaCatalogRepository.save(new CarEntity(id, model, brand, color, year, price)).toModel();
    }

    @Override
    public boolean deleteCar(UUID id) {
        try {
            jpaCatalogRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            // If the car does not exist, deleteById throws an EmptyResultDataAccessException
            // So we "cast" it to a simple false
            return false;
        }
    }

    @Override
    public Car addCar(String model, String brand, String color, Integer year, BigDecimal price) {
        return jpaCatalogRepository.save(new CarEntity(model, brand, color, year, price)).toModel();
    }

    @Override
    public List<Car> searchCar(String model, String brand, String color, Integer year, BigDecimal priceMin, BigDecimal priceMax) {
        return StreamSupport
                .stream(jpaCatalogRepository.findAllByOptionalEntityFields(model, brand, color, year, priceMin, priceMax).spliterator(), false)
                .map(CarEntity::toModel)
                .collect(Collectors.toList());
    }
}
