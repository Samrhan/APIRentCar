package com.bader.infrastructure.persitence.todo.entity;

import com.bader.domain.todo.model.ToDo;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.PersistenceCreator;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "TODO")
public class ToDoEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "COMPLETED", nullable = false)
    private boolean completed;

    @Column(name = "SLOT", nullable = false)
    private int order;

    @PersistenceCreator
    public ToDoEntity(UUID id, String title, Boolean completed, Integer order) {
        this.title = title;
        this.completed = completed;
        this.order = order;
        this.id = id;
    }

    public ToDoEntity(String title, int order) {
        this.title = title;
        this.order = order;
        this.completed = false;
    }

    protected ToDoEntity() {
    }

    public ToDoEntity(ToDo toDo) {
        this.id = toDo.getId();
        this.title = toDo.getTitle();
        this.completed = toDo.isCompleted();
        this.order = toDo.getOrder();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDoEntity that = (ToDoEntity) o;
        return (
                completed == that.completed &&
                        order == that.order &&
                        Objects.equals(id, that.id) &&
                        Objects.equals(title, that.title)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, completed, order);
    }

    public ToDo toModel() {
        return new ToDo(this.id, this.title, this.order, this.completed);
    }
}
