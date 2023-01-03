package com.bader;

import com.bader.domain.todo.RepositoryBasedToDoService;
import com.bader.domain.todo.exception.OrderConflictException;
import com.bader.domain.todo.model.ToDo;
import com.bader.domain.todo.repository.ToDoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RepositoryBasedToDoServiceTests {

    @Mock
    private ToDoRepository toDoRepository;

    @InjectMocks
    private RepositoryBasedToDoService repositoryBasedToDoService;

    @Test
    void shouldCreateToDoWhenThereIsAlreadyATodoInDb() {
        ToDo toDo = new ToDo(UUID.randomUUID(), "title", 2, false);
        ToDo savedToDo = new ToDo("title", 2);
        when(toDoRepository.getMaxOrder()).thenReturn(1);
        when(toDoRepository.save(any())).thenReturn(toDo);

        ToDo newToDo = repositoryBasedToDoService.createToDo("title");

        assertThat(newToDo).usingRecursiveComparison().isEqualTo(toDo);

        InOrder inOrder = inOrder(toDoRepository);

        inOrder.verify(toDoRepository).getMaxOrder();

        ArgumentCaptor<ToDo> argument = ArgumentCaptor.forClass(ToDo.class);
        inOrder.verify(toDoRepository).save(argument.capture());
        inOrder.verifyNoMoreInteractions();

        assertThat(argument.getValue())
                .usingRecursiveComparison()
                .isEqualTo(savedToDo);
    }

    @Test
    void shouldCreateToDoWhenThereIsNoTodoInDb() {
        ToDo toDo = new ToDo(UUID.randomUUID(), "title", 1, false);
        ToDo savedToDo = new ToDo("title", 1);
        when(toDoRepository.getMaxOrder()).thenReturn(0);
        when(toDoRepository.save(any())).thenReturn(toDo);

        ToDo newToDo = repositoryBasedToDoService.createToDo("title");

        assertThat(newToDo).usingRecursiveComparison().isEqualTo(toDo);

        InOrder inOrder = inOrder(toDoRepository);

        inOrder.verify(toDoRepository).getMaxOrder();
        ArgumentCaptor<ToDo> argument = ArgumentCaptor.forClass(ToDo.class);
        inOrder.verify(toDoRepository).save(argument.capture());
        inOrder.verifyNoMoreInteractions();

        assertThat(argument.getValue())
                .usingRecursiveComparison()
                .isEqualTo(savedToDo);
    }

    @Test
    void shouldGetAllToDo() {
        ToDo toDo = new ToDo("title", 1);
        when(toDoRepository.findAll()).thenReturn(List.of(toDo));

        List<ToDo> toDos = repositoryBasedToDoService.getAllToDo();

        assertThat(toDos).usingRecursiveComparison().isEqualTo(List.of(toDo));

        verify(toDoRepository).findAll();
        verifyNoMoreInteractions(toDoRepository);
    }

    @Test
    void shouldGetAllCompletedToDo() {
        ToDo toDo = new ToDo("title", 1);
        when(toDoRepository.findAllByCompleted(true)).thenReturn(List.of(toDo));

        List<ToDo> toDos = repositoryBasedToDoService.getToDoByCompleted(true);

        assertThat(toDos).usingRecursiveComparison().isEqualTo(List.of(toDo));

        verify(toDoRepository).findAllByCompleted(true);
        verifyNoMoreInteractions(toDoRepository);
    }

    @Test
    void shouldGetAValidToDoOptionalGivenAValidId() {
        UUID id = UUID.randomUUID();
        ToDo toDo = new ToDo("title", 1);
        when(toDoRepository.getTodo(id)).thenReturn(Optional.of(toDo));

        Optional<ToDo> askedToDo = repositoryBasedToDoService.getToDo(id);

        assertThat(askedToDo)
                .usingRecursiveComparison()
                .isEqualTo(Optional.of(toDo));

        verify(toDoRepository).getTodo(id);
        verifyNoMoreInteractions(toDoRepository);
    }

    @Test
    void shouldReturnAnEmptyOptionalWhenGivenABadId() {
        UUID id = UUID.randomUUID();
        when(toDoRepository.getTodo(id)).thenReturn(Optional.empty());

        Optional<ToDo> toDo = repositoryBasedToDoService.getToDo(id);

        assertThat(toDo.isPresent()).isFalse();

        verify(toDoRepository).getTodo(id);
        verifyNoMoreInteractions(toDoRepository);
    }

    @Test
    void shouldUpdateToDoTitleWhenGivenAValidIdAndAValidTitle() {
        UUID id = UUID.randomUUID();
        ToDo toDo = new ToDo("title", 1);
        when(toDoRepository.getTodo(id)).thenReturn(Optional.of(toDo));
        when(toDoRepository.save(any())).thenReturn(toDo);

        Optional<ToDo> updatedToDo = repositoryBasedToDoService.updateToDo(
                id,
                null,
                "new title",
                null
        );

        assertThat(updatedToDo)
                .usingRecursiveComparison()
                .isEqualTo(Optional.of(toDo));

        InOrder inOrder = inOrder(toDoRepository);

        inOrder.verify(toDoRepository).getTodo(id);
        inOrder.verify(toDoRepository).save(toDo);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldUpdateToDoCompletenessWhenGivenAValidIdAndAValidCompleteness() {
        UUID id = UUID.randomUUID();
        ToDo toDo = new ToDo("title", 1);
        when(toDoRepository.getTodo(id)).thenReturn(Optional.of(toDo));
        when(toDoRepository.save(any())).thenReturn(toDo);

        Optional<ToDo> updatedToDo = repositoryBasedToDoService.updateToDo(
                id,
                true,
                null,
                null
        );

        assertThat(updatedToDo)
                .usingRecursiveComparison()
                .isEqualTo(Optional.of(toDo));

        InOrder inOrder = inOrder(toDoRepository);

        inOrder.verify(toDoRepository).getTodo(id);
        inOrder.verify(toDoRepository).save(toDo);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldUpdateToDoOrderWhenGivenAVValidIdAndAValidOrder() {
        UUID id = UUID.randomUUID();
        ToDo toDo = new ToDo("title", 1);
        when(toDoRepository.getTodo(id)).thenReturn(Optional.of(toDo));
        when(toDoRepository.findByOrder(anyInt())).thenReturn(Optional.empty());
        when(toDoRepository.save(any())).thenReturn(toDo);

        Optional<ToDo> updatedToDo = repositoryBasedToDoService.updateToDo(
                id,
                null,
                null,
                1
        );

        assertThat(updatedToDo)
                .usingRecursiveComparison()
                .isEqualTo(Optional.of(toDo));

        InOrder inOrder = inOrder(toDoRepository);

        inOrder.verify(toDoRepository).getTodo(id);
        inOrder.verify(toDoRepository).findByOrder(1);
        inOrder.verify(toDoRepository).save(toDo);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldUpdateToDoOrderWhenGivenAVValidIdAndAConflictingOrderWithItself() {
        UUID id = UUID.randomUUID();
        ToDo toDo = new ToDo(id, "title", 1, false);
        when(toDoRepository.getTodo(id)).thenReturn(Optional.of(toDo));
        when(toDoRepository.findByOrder(anyInt())).thenReturn(Optional.of(toDo));
        when(toDoRepository.save(any())).thenReturn(toDo);

        Optional<ToDo> updatedToDo = repositoryBasedToDoService.updateToDo(
                id,
                null,
                null,
                1
        );

        assertThat(updatedToDo)
                .usingRecursiveComparison()
                .isEqualTo(Optional.of(toDo));

        InOrder inOrder = inOrder(toDoRepository);

        inOrder.verify(toDoRepository).getTodo(id);
        inOrder.verify(toDoRepository).findByOrder(1);
        inOrder.verify(toDoRepository).save(toDo);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldThrowExceptionWhenUpdatingGivenAConflictOrder() {
        UUID id = UUID.randomUUID();
        ToDo toDo = new ToDo(id, "title", 1, false);
        ToDo conflictingToDo = new ToDo(UUID.randomUUID(), "title", 1, false);
        when(toDoRepository.getTodo(id)).thenReturn(Optional.of(toDo));
        when(toDoRepository.findByOrder(anyInt()))
                .thenReturn(Optional.of(conflictingToDo));

        assertThrows(
                OrderConflictException.class,
                () -> repositoryBasedToDoService.updateToDo(id, null, null, 1)
        );

        InOrder inOrder = inOrder(toDoRepository);

        inOrder.verify(toDoRepository).getTodo(id);
        inOrder.verify(toDoRepository).findByOrder(1);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldUpdateFullToDoWhenGivenAVValidIdAndAValidToDo() {
        UUID id = UUID.randomUUID();
        ToDo toDo = new ToDo("title", 1);
        when(toDoRepository.getTodo(id)).thenReturn(Optional.of(toDo));
        when(toDoRepository.findByOrder(anyInt())).thenReturn(Optional.empty());
        when(toDoRepository.save(any())).thenReturn(toDo);

        Optional<ToDo> updatedToDo = repositoryBasedToDoService.updateToDo(
                id,
                true,
                "new title",
                1
        );

        assertThat(updatedToDo)
                .usingRecursiveComparison()
                .isEqualTo(Optional.of(toDo));

        InOrder inOrder = inOrder(toDoRepository);

        inOrder.verify(toDoRepository).getTodo(id);
        inOrder.verify(toDoRepository).findByOrder(1);
        inOrder.verify(toDoRepository).save(toDo);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldReturnAnEmptyOptionalWhenUpdatingGivenAUnknownId() {
        UUID id = UUID.randomUUID();
        when(toDoRepository.getTodo(id)).thenReturn(Optional.empty());

        Optional<ToDo> toDo = repositoryBasedToDoService.updateToDo(
                id,
                null,
                null,
                null
        );

        assertThat(toDo.isEmpty()).isTrue();

        verify(toDoRepository).getTodo(id);
        verifyNoMoreInteractions(toDoRepository);
    }

    @Test
    void shouldDeleteToDoWhenGivenAValidId() {
        UUID id = UUID.randomUUID();
        when(toDoRepository.deleteToDo(id)).thenReturn(1);

        Optional<Boolean> state = repositoryBasedToDoService.deleteToDo(id);

        assertThat(state.isPresent()).isTrue();

        verify(toDoRepository).deleteToDo(id);
        verifyNoMoreInteractions(toDoRepository);
    }

    @Test
    void shouldReturnEmptyOptionalWhenDeletingATodoGivenAUnknownId() {
        UUID id = UUID.randomUUID();
        when(toDoRepository.deleteToDo(id)).thenReturn(0);

        Optional<Boolean> state = repositoryBasedToDoService.deleteToDo(id);

        assertThat(state.isEmpty()).isTrue();

        verify(toDoRepository).deleteToDo(id);
        verifyNoMoreInteractions(toDoRepository);
    }

    @Test
    void shouldDeleteAllToDo() {
        doNothing().when(toDoRepository).deleteAll();

        repositoryBasedToDoService.deleteAll();

        verify(toDoRepository).deleteAll();
        verifyNoMoreInteractions(toDoRepository);
    }
}
