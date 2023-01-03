package com.bader.domain.todo.model;

import java.util.UUID;

public class ToDo {

    private UUID id;

    private String title;

    private int order;

    private boolean completed;

    public ToDo(UUID id, String title, int order, boolean completed) {
        this.id = id;
        this.title = title;
        this.order = order;
        this.completed = completed;
    }

    public ToDo(String title, int order) {
        this.title = title;
        this.order = order;
        this.completed = false;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
