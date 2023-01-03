package com.bader.ports.web.todo.dto.response;

import com.bader.domain.todo.model.ToDo;

import java.util.UUID;

public class ToDoResponse {

    private final UUID id;

    private final String title;

    private final int order;

    private final boolean completed;

    private final String url;

    public ToDoResponse(
            UUID id,
            String title,
            int order,
            boolean completed,
            String url
    ) {
        this.id = id;
        this.title = title;
        this.order = order;
        this.completed = completed;
        this.url = url;
    }

    public ToDoResponse(ToDo toDo, String url) {
        this(
                toDo.getId(),
                toDo.getTitle(),
                toDo.getOrder(),
                toDo.isCompleted(),
                url
        );
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getOrder() {
        return order;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getUrl() {
        return url;
    }
}
