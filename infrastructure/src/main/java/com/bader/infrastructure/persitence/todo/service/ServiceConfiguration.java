package com.bader.infrastructure.persitence.todo.service;

import com.bader.domain.todo.RepositoryBasedToDoService;
import com.bader.domain.todo.repository.ToDoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {
    @Bean()
    public RepositoryBasedToDoService repositoryBasedToDoService(ToDoRepository toDoRepository) {
        return new RepositoryBasedToDoService(toDoRepository);
    }
}
