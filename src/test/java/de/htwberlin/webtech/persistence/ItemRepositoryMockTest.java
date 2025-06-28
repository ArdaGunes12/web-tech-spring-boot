package de.htwberlin.webtech.persistence;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ItemRepositoryMockTest {

    @Test
    void testFindById() {
        ItemRepository itemRepo = mock(ItemRepository.class);
        Item testItem = new Item("Milch", "Getränke", 1, false, 1.49);
        testItem.setId(1L);

        when(itemRepo.findById(1L)).thenReturn(Optional.of(testItem));

        Optional<Item> result = itemRepo.findById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Milch");

        verify(itemRepo).findById(1L);
    }
}