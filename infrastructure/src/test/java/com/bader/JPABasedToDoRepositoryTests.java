package com.bader;

import com.bader.domain.todo.model.ToDo;
import com.bader.infrastructure.persitence.todo.JPABasedToDoRepository;
import com.bader.infrastructure.persitence.todo.JPAToDoRepository;
import com.bader.infrastructure.persitence.todo.entity.ToDoEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JPABasedToDoRepositoryTests {

    @Mock
    private JPAToDoRepository jpaToDoRepository;

    @InjectMocks
    private JPABasedToDoRepository sut;

    @Test
    void shouldGetMaxOrderWhenThereIsAToDoInDb() {
        when(jpaToDoRepository.getMaxOrder()).thenReturn(Optional.of(1));

        assertThat(sut.getMaxOrder()).isEqualTo(1);

        verify(jpaToDoRepository).getMaxOrder();
        verifyNoMoreInteractions(jpaToDoRepository);
    }

    @Test
    void shouldGetMaxOrderWhenThereIsNoToDoInDb() {
        when(jpaToDoRepository.getMaxOrder()).thenReturn(Optional.empty());

        assertThat(sut.getMaxOrder()).isEqualTo(0);

        verify(jpaToDoRepository).getMaxOrder();
        verifyNoMoreInteractions(jpaToDoRepository);
    }

    @Test
    void shouldGetToDoGivenAValidId() {
        UUID id = UUID.randomUUID();
        ToDoEntity toDoEntity = new ToDoEntity(id, "title", false, 1);

        when(jpaToDoRepository.getTodo(any())).thenReturn(Optional.of(toDoEntity));
        Optional<ToDo> toDo = sut.getTodo(id);
        assertThat(toDo)
                .usingRecursiveComparison()
                .isEqualTo(Optional.of(toDoEntity.toModel()));

        verify(jpaToDoRepository).getTodo(id);
        verifyNoMoreInteractions(jpaToDoRepository);
    }

    @Test
    void shouldReturnEmptyOptionalGivenAUnknownId() {
        UUID id = UUID.randomUUID();

        when(jpaToDoRepository.getTodo(any())).thenReturn(Optional.empty());
        Optional<ToDo> toDo = sut.getTodo(id);
        assertThat(toDo).usingRecursiveComparison().isEqualTo(Optional.empty());

        verify(jpaToDoRepository).getTodo(id);
        verifyNoMoreInteractions(jpaToDoRepository);
    }

    @Test
    void shouldDeleteToDoGivenAValidId() {
        UUID id = UUID.randomUUID();

        when(jpaToDoRepository.deleteToDo(any())).thenReturn(1);
        Integer deleted = sut.deleteToDo(id);
        assertThat(deleted).isEqualTo(1);

        verify(jpaToDoRepository).deleteToDo(id);
        verifyNoMoreInteractions(jpaToDoRepository);
    }

    @Test
    void shouldReturn0WhileDeletingAToDoGivenAnInvalidId() {
        UUID id = UUID.randomUUID();

        when(jpaToDoRepository.deleteToDo(any())).thenReturn(0);
        Integer deleted = sut.deleteToDo(id);
        assertThat(deleted).isEqualTo(0);

        verify(jpaToDoRepository).deleteToDo(id);
        verifyNoMoreInteractions(jpaToDoRepository);
    }

    @Test
    void shouldReturnAllCompletedToDo() {
        ToDoEntity toDoEntity = new ToDoEntity(
                UUID.randomUUID(),
                "title",
                false,
                1
        );

        when(jpaToDoRepository.findAllByCompleted(anyBoolean()))
                .thenReturn(List.of(toDoEntity));
        List<ToDo> toDos = sut.findAllByCompleted(true);
        assertThat(toDos)
                .usingRecursiveComparison()
                .isEqualTo(List.of(toDoEntity.toModel()));

        verify(jpaToDoRepository).findAllByCompleted(true);
        verifyNoMoreInteractions(jpaToDoRepository);
    }

    @Test
    void shouldReturnEmptyListWhenThereIsNoCompletedToDo() {
        when(jpaToDoRepository.findAllByCompleted(anyBoolean()))
                .thenReturn(List.of());
        List<ToDo> toDos = sut.findAllByCompleted(true);
        assertThat(toDos).usingRecursiveComparison().isEqualTo(List.of());

        verify(jpaToDoRepository).findAllByCompleted(true);
        verifyNoMoreInteractions(jpaToDoRepository);
    }

    @Test
    void shouldReturnAllUncompletedToDo() {
        ToDoEntity toDoEntity = new ToDoEntity(
                UUID.randomUUID(),
                "title",
                false,
                1
        );

        when(jpaToDoRepository.findAllByCompleted(anyBoolean()))
                .thenReturn(List.of(toDoEntity));
        List<ToDo> toDos = sut.findAllByCompleted(false);
        assertThat(toDos)
                .usingRecursiveComparison()
                .isEqualTo(List.of(toDoEntity.toModel()));

        verify(jpaToDoRepository).findAllByCompleted(false);
        verifyNoMoreInteractions(jpaToDoRepository);
    }

    @Test
    void shouldReturnEmptyListWhenThereIsNoUncompletedToDo() {
        when(jpaToDoRepository.findAllByCompleted(anyBoolean()))
                .thenReturn(List.of());
        List<ToDo> toDos = sut.findAllByCompleted(false);
        assertThat(toDos).usingRecursiveComparison().isEqualTo(List.of());

        verify(jpaToDoRepository).findAllByCompleted(false);
        verifyNoMoreInteractions(jpaToDoRepository);
    }

    @Test
    void shouldReturnToDoGivenAnExistingOrder() {
        ToDoEntity toDoEntity = new ToDoEntity(
                UUID.randomUUID(),
                "title",
                false,
                1
        );

        when(jpaToDoRepository.findByOrder(anyInt()))
                .thenReturn(Optional.of(toDoEntity));
        Optional<ToDo> toDo = sut.findByOrder(1);
        assertThat(toDo)
                .usingRecursiveComparison()
                .isEqualTo(Optional.of(toDoEntity.toModel()));

        verify(jpaToDoRepository).findByOrder(1);
        verifyNoMoreInteractions(jpaToDoRepository);
    }

    @Test
    void shouldReturnEmptyOptionalGivenANonExistingOrder() {
        when(jpaToDoRepository.findByOrder(anyInt())).thenReturn(Optional.empty());
        Optional<ToDo> toDo = sut.findByOrder(1);
        assertThat(toDo).usingRecursiveComparison().isEqualTo(Optional.empty());

        verify(jpaToDoRepository).findByOrder(1);
        verifyNoMoreInteractions(jpaToDoRepository);
    }

    @Test
    void shouldReturnAllToDo() {
        ToDoEntity toDoEntity = new ToDoEntity(
                UUID.randomUUID(),
                "title",
                false,
                1
        );

        when(jpaToDoRepository.findAll()).thenReturn(List.of(toDoEntity));
        List<ToDo> toDos = sut.findAll();
        assertThat(toDos)
                .usingRecursiveComparison()
                .isEqualTo(List.of(toDoEntity.toModel()));

        verify(jpaToDoRepository).findAll();
        verifyNoMoreInteractions(jpaToDoRepository);
    }

    @Test
    void shouldReturnEmptyListWhenThereIsNoToDo() {
        when(jpaToDoRepository.findAll()).thenReturn(List.of());
        List<ToDo> toDos = sut.findAll();
        assertThat(toDos).usingRecursiveComparison().isEqualTo(List.of());

        verify(jpaToDoRepository).findAll();
        verifyNoMoreInteractions(jpaToDoRepository);
    }

    @Test
    void shouldSaveToDo() {
        ToDoEntity toDoEntity = new ToDoEntity(
                UUID.randomUUID(),
                "title",
                false,
                1
        );
        ToDo toDo = toDoEntity.toModel();

        when(jpaToDoRepository.save(any())).thenReturn(toDoEntity);
        ToDo savedToDo = sut.save(toDo);
        assertThat(savedToDo).usingRecursiveComparison().isEqualTo(toDo);

        verify(jpaToDoRepository).save(toDoEntity);
        verifyNoMoreInteractions(jpaToDoRepository);
    }

    @Test
    void shouldDeleteAll() {
        sut.deleteAll();
        verify(jpaToDoRepository).deleteAll();
        verifyNoMoreInteractions(jpaToDoRepository);
    }
}
