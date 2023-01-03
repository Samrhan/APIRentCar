package com.bader;

import com.bader.domain.todo.ToDoService;
import com.bader.domain.todo.exception.OrderConflictException;
import com.bader.domain.todo.model.ToDo;
import com.bader.ports.web.todo.ToDoController;
import com.bader.ports.web.todo.dto.request.CreateToDo;
import com.bader.ports.web.todo.dto.response.ToDoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {ToDoController.class})
@WebMvcTest
public class ToDoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ToDoService service;

    @Test
    void shouldCreateToDoGivenAValidCreationRequest() throws Exception {
        UUID id = UUID.randomUUID();
        ToDo todo = new ToDo(id, "todo", 0, false);
        when(service.createToDo(any())).thenReturn(todo);

        ToDoResponse toDoResponse = new ToDoResponse(
                todo,
                "http://localhost/todos/" + id
        );
        mockMvc
                .perform(
                        post("/todos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new CreateToDo("todo")))
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        content().json(objectMapper.writeValueAsString(toDoResponse), true)
                );

        verify(service).createToDo("todo");
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldNotCreateToDoGivenAnEmptyCreationRequest() throws Exception {
        mockMvc
                .perform(
                        post("/todos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotCreateToDoGivenABadCreationRequest() throws Exception {
        mockMvc
                .perform(
                        post("/todos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content("{\"test\": \"test\"}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetAllToDos() throws Exception {
        UUID id = UUID.randomUUID();

        ToDo toDo = new ToDo(id, "todo", 0, false);
        List<ToDo> toDos = List.of(toDo);
        when(service.getAllToDo()).thenReturn(toDos);

        ToDoResponse toDoResponse = new ToDoResponse(
                toDo,
                "http://localhost/todos/" + id
        );
        List<ToDoResponse> toDoResponses = List.of(toDoResponse);

        mockMvc
                .perform(
                        get("/todos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        content().json(objectMapper.writeValueAsString(toDoResponses), true)
                );

        verify(service).getAllToDo();
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldGetAllCompletedTodos() throws Exception {
        UUID id = UUID.randomUUID();

        ToDo toDo = new ToDo(id, "todo", 0, true);
        List<ToDo> toDos = List.of(toDo);
        when(service.getToDoByCompleted(true)).thenReturn(toDos);

        ToDoResponse toDoResponse = new ToDoResponse(
                toDo,
                "http://localhost/todos/" + id
        );
        List<ToDoResponse> toDoResponses = List.of(toDoResponse);

        mockMvc
                .perform(
                        get("/todos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .param("completed", "")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        content().json(objectMapper.writeValueAsString(toDoResponses), true)
                );

        verify(service).getToDoByCompleted(true);
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldGetToDoGivenAValidId() throws Exception {
        UUID id = UUID.randomUUID();
        ToDo toDo = new ToDo(id, "todo", 0, false);
        when(service.getToDo(id)).thenReturn(Optional.of(toDo));

        ToDoResponse toDoResponse = new ToDoResponse(
                toDo,
                "http://localhost/todos/" + id
        );

        mockMvc
                .perform(
                        get("/todos/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        content().json(objectMapper.writeValueAsString(toDoResponse), true)
                );

        verify(service).getToDo(id);
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldNotGetToDoGivenAnInvalidId() throws Exception {
        UUID id = UUID.randomUUID();
        when(service.getToDo(id)).thenReturn(Optional.empty());

        mockMvc
                .perform(
                        get("/todos/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());

        verify(service).getToDo(id);
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldUpdateToDoGivenAValidId() throws Exception {
        UUID id = UUID.randomUUID();
        String title = "todo";
        int order = 0;
        boolean completed = false;
        ToDo toDo = new ToDo(id, title, order, completed);

        when(service.updateToDo(id, true, null, null))
                .thenReturn(Optional.of(toDo));

        ToDoResponse toDoResponse = new ToDoResponse(
                toDo,
                "http://localhost/todos/" + id
        );

        mockMvc
                .perform(
                        patch("/todos/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content("{\"completed\": true}")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        content().json(objectMapper.writeValueAsString(toDoResponse), true)
                );

        verify(service).updateToDo(id, true, null, null);
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldNotUpdateToDoGivenAnInvalidId() throws Exception {
        UUID id = UUID.randomUUID();
        when(service.updateToDo(id, true, null, 0)).thenReturn(Optional.empty());

        mockMvc
                .perform(
                        patch("/todos/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content("{\"completed\": true}")
                )
                .andExpect(status().isNotFound());

        verify(service).updateToDo(id, true, null, null);
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldNotUpdateToDoGivenANegativeOrder() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc
                .perform(
                        patch("/todos/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content("{\"order\": -1}")
                )
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldNotUpdateToDoGivenABlankTitle(String title) throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc
                .perform(
                        patch("/todos/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content("{\"title\": \"%s\"}".formatted(title))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotUpdateToDoGivenAConflitOrder() throws Exception {
        UUID id = UUID.randomUUID();
        when(service.updateToDo(any(), any(), any(), any()))
                .thenThrow(OrderConflictException.class);

        mockMvc
                .perform(
                        patch("/todos/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content("{\"order\": 0}")
                )
                .andExpect(status().isConflict());

        verify(service).updateToDo(id, null, null, 0);
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldDeleteToDoGivenAValidId() throws Exception {
        UUID id = UUID.randomUUID();
        when(service.deleteToDo(id)).thenReturn(Optional.of(true));

        mockMvc
                .perform(
                        delete("/todos/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());

        verify(service).deleteToDo(id);
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldThrow404OnDeletingATodoGivenAnInvalidId() throws Exception {
        UUID id = UUID.randomUUID();
        when(service.deleteToDo(id)).thenReturn(Optional.empty());

        mockMvc
                .perform(
                        delete("/todos/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());

        verify(service).deleteToDo(id);
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldDeleteAllToDos() throws Exception {
        doNothing().when(service).deleteAll();

        mockMvc
                .perform(
                        delete("/todos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());

        verify(service).deleteAll();
        verifyNoMoreInteractions(service);
    }
}
