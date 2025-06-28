package de.htwberlin.webtech.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.htwberlin.webtech.persistence.Item;
import de.htwberlin.webtech.persistence.ItemRepository;
import de.htwberlin.webtech.persistence.ShoppingList;
import de.htwberlin.webtech.persistence.ShoppingListRepository;
import de.htwberlin.webtech.web.dto.ItemDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllItems() throws Exception {
        Item item = new Item("Milch", "Getränke", 1, false, 1.49);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Milch")));
    }

    @Test
    void shouldReturnItemById() throws Exception {
        Item item = new Item("Brot", "Backwaren", 2, true, 2.29);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        mockMvc.perform(get("/api/items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Brot")));
    }

    @Test
    void shouldCreateNewItem() throws Exception {
        ShoppingList list = new ShoppingList();
        list.setId(1L);
        Item item = new Item("Apfel", "Obst", 3, false, 0.99);
        item.setShoppingList(list);
        ItemDto dto = new ItemDto(null, "Apfel", "Obst", 3, false, 0.99, 1L);

        when(shoppingListRepository.findById(1L)).thenReturn(Optional.of(list));
        when(itemRepository.save(any())).thenReturn(item);

        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Apfel")));
    }
}
