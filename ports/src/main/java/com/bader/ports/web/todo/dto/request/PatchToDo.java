package com.bader.ports.web.todo.dto.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;

public class PatchToDo {

    @Length(min = 1)
    private String title;

    private Boolean completed;

    @Min(0)
    private Integer order;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.trim();
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Integer getOrder() {
        return order;
    }
}
