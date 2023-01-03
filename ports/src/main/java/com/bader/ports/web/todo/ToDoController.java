package com.bader.ports.web.todo;

import com.bader.domain.todo.ToDoService;
import com.bader.domain.todo.exception.OrderConflictException;
import com.bader.domain.todo.model.ToDo;
import com.bader.ports.web.todo.dto.request.CreateToDo;
import com.bader.ports.web.todo.dto.request.PatchToDo;
import com.bader.ports.web.todo.dto.response.ToDoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todos")
public class ToDoController {

    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping
    public List<ToDoResponse> getAllTodos(
            @RequestParam(required = false) String completed
    ) {
        if (completed == null) {
            return this.toDoService.getAllToDo()
                    .stream()
                    .map(this::toToDoResponse)
                    .collect(Collectors.toList());
        } else {
            return toDoService
                    .getToDoByCompleted(true)
                    .stream()
                    .map(this::toToDoResponse)
                    .collect(Collectors.toList());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDoResponse> getToDoById(@PathVariable("id") UUID id) {
        return ResponseEntity.of(
                this.toDoService.getToDo(id).map(this::toToDoResponse)
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ToDoResponse createToDo(
            @RequestBody @Valid CreateToDo createToDo
    ) {
        return toToDoResponse(
                this.toDoService.createToDo(createToDo.getTitle())
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ToDoResponse> updateToDo(
            @PathVariable("id") UUID id,
            @RequestBody @Valid PatchToDo patchedToDo
    ) {
        try {
            return ResponseEntity.of(
                    this.toDoService.updateToDo(
                                    id,
                                    patchedToDo.getCompleted(),
                                    patchedToDo.getTitle(),
                                    patchedToDo.getOrder()
                            )
                            .map(this::toToDoResponse)
            );
        } catch (OrderConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllToDo(@RequestParam(required = false) String completed) {
        if (completed == null) {
            this.toDoService.deleteAll();
        } else {
            this.toDoService.deleteAllByCompleted(true);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteToDoById(@PathVariable("id") UUID id) {
        if (this.toDoService.deleteToDo(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    private ToDoResponse toToDoResponse(ToDo toDo) {
        return new ToDoResponse(
                toDo,
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .pathSegment("todos", "{id}")
                        .buildAndExpand(toDo.getId())
                        .toUriString()
        );
    }
}
