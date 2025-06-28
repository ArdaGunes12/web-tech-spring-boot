package de.htwberlin.webtech.persistence;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ShoppingListRepositoryMockTest {

    @Test
    void testFindById() {
        ShoppingListRepository listRepo = mock(ShoppingListRepository.class);
        ShoppingList list = new ShoppingList();
        list.setId(1L);
        list.setName("Woche KW27");
        list.setPlannedDate(LocalDate.of(2025, 7, 1));

        when(listRepo.findById(1L)).thenReturn(Optional.of(list));

        Optional<ShoppingList> result = listRepo.findById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Woche KW27");

        verify(listRepo).findById(1L);
    }
}