package com.bader.infrastructure.persitence.catalog;

import com.bader.infrastructure.persitence.catalog.entity.CarEntity;
import com.bader.infrastructure.persitence.todo.entity.ToDoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface JPACatalogRepository extends CrudRepository<CarEntity, Integer> {
}
