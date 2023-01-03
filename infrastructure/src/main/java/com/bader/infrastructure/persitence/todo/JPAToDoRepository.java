package com.bader.infrastructure.persitence.todo;

import com.bader.infrastructure.persitence.todo.entity.ToDoEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public interface JPAToDoRepository extends CrudRepository<ToDoEntity, Integer> {
    @Query("SELECT MAX(t.order) FROM ToDoEntity t")
    Optional<Integer> getMaxOrder();

    @Query("SELECT t FROM ToDoEntity t WHERE t.id = :id")
    Optional<ToDoEntity> getTodo(@Param("id") UUID id);

    @Modifying
    @Transactional
    @Query("DELETE FROM ToDoEntity t WHERE t.id = :id")
    Integer deleteToDo(@Param("id") UUID id);

    @Query("SELECT t FROM ToDoEntity t WHERE t.completed = :completed")
    Iterable<ToDoEntity> findAllByCompleted(
            @Param("completed") boolean completed
    );

    @Query("SELECT t FROM ToDoEntity t WHERE t.order = :order")
    Optional<ToDoEntity> findByOrder(@Param("order") int order);

    @Modifying
    @Transactional
    @Query("DELETE FROM ToDoEntity t WHERE t.completed = :completed")
    void deleteAllByCompleted(@Param("completed") boolean completed);
}
