package com.bader.domain.todo.repository;

import com.bader.domain.todo.model.ToDo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ToDoRepository {
    Integer getMaxOrder();

    Optional<ToDo> getTodo(UUID id);

    Integer deleteToDo(UUID id);

    List<ToDo> findAllByCompleted(boolean completed);

    Optional<ToDo> findByOrder(int order);

    void deleteAllByCompleted(boolean completed);

    List<ToDo> findAll();

    ToDo save(ToDo toDo);

    void deleteAll();
}
