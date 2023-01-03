package com.bader.domain.todo;

import com.bader.domain.todo.model.ToDo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ToDoService {
    List<ToDo> getAllToDo();

    List<ToDo> getToDoByCompleted(boolean completed);

    ToDo createToDo(String title);

    Optional<ToDo> getToDo(UUID id);

    Optional<ToDo> updateToDo(
            UUID id,
            Boolean completed,
            String title,
            Integer order
    );

    void deleteAll();

    Optional<Boolean> deleteToDo(UUID id);

    void deleteAllByCompleted(boolean completed);
}
