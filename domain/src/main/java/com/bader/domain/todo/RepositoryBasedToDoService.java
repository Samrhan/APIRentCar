package com.bader.domain.todo;

import com.bader.domain.todo.exception.OrderConflictException;
import com.bader.domain.todo.model.ToDo;
import com.bader.domain.todo.repository.ToDoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryBasedToDoService implements ToDoService {

    private final ToDoRepository toDoRepository;

    public RepositoryBasedToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @Override
    public ToDo createToDo(String title) {
        ToDo toDo = new ToDo(title, toDoRepository.getMaxOrder() + 1);

        return toDoRepository.save(toDo);
    }

    @Override
    public List<ToDo> getAllToDo() {
        return toDoRepository.findAll();
    }

    @Override
    public Optional<ToDo> getToDo(UUID id) {
        return toDoRepository.getTodo(id);
    }

    @Override
    public Optional<ToDo> updateToDo(
            UUID id,
            Boolean completed,
            String title,
            Integer order
    ) {
        return toDoRepository
                .getTodo(id)
                .map(toDo -> {
                    if (title != null) {
                        toDo.setTitle(title);
                    }
                    if (completed != null) {
                        toDo.setCompleted(completed);
                    }
                    return toDo;
                })
                .map(toDo -> {
                    if (order != null) {
                        // throw 409 Exception if order is already taken
                        toDoRepository
                                .findByOrder(order)
                                .ifPresent(conflictEntity -> {
                                    if (!conflictEntity.getId().equals(id)) {
                                        throw new OrderConflictException();
                                    }
                                });
                        toDo.setOrder(order);
                    }
                    return toDo;
                })
                .map(toDoRepository::save);
    }

    @Override
    public void deleteAll() {
        toDoRepository.deleteAll();
    }

    @Override
    public void deleteAllByCompleted(boolean completed) {
        toDoRepository.deleteAllByCompleted(completed);
    }

    @Override
    public Optional<Boolean> deleteToDo(UUID id) {
        Integer deleted = toDoRepository.deleteToDo(id);
        return deleted > 0 ? Optional.of(true) : Optional.empty();
    }

    @Override
    public List<ToDo> getToDoByCompleted(boolean completed) {
        return toDoRepository.findAllByCompleted(completed);
    }
}
