package com.bader.infrastructure.persitence.todo;

import com.bader.domain.todo.model.ToDo;
import com.bader.domain.todo.repository.ToDoRepository;
import com.bader.infrastructure.persitence.todo.entity.ToDoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class JPABasedToDoRepository implements ToDoRepository {

    private final JPAToDoRepository jpaToDoRepository;

    public JPABasedToDoRepository(JPAToDoRepository jpaToDoRepository) {
        this.jpaToDoRepository = jpaToDoRepository;
    }

    @Override
    public Integer getMaxOrder() {
        return jpaToDoRepository.getMaxOrder().orElse(0);
    }

    @Override
    public Optional<ToDo> getTodo(UUID id) {
        return jpaToDoRepository.getTodo(id).map(ToDoEntity::toModel);
    }

    @Override
    public Integer deleteToDo(UUID id) {
        return jpaToDoRepository.deleteToDo(id);
    }

    @Override
    public List<ToDo> findAllByCompleted(boolean completed) {
        return StreamSupport
                .stream(
                        jpaToDoRepository.findAllByCompleted(completed).spliterator(),
                        false
                )
                .map(ToDoEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ToDo> findByOrder(int order) {
        return jpaToDoRepository.findByOrder(order).map(ToDoEntity::toModel);
    }

    @Override
    public void deleteAllByCompleted(boolean completed) {
        jpaToDoRepository.deleteAllByCompleted(completed);
    }

    @Override
    public List<ToDo> findAll() {
        return StreamSupport
                .stream(jpaToDoRepository.findAll().spliterator(), false)
                .map(ToDoEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public ToDo save(ToDo toDo) {
        return jpaToDoRepository.save(new ToDoEntity(toDo)).toModel();
    }

    @Override
    public void deleteAll() {
        jpaToDoRepository.deleteAll();
    }
}
