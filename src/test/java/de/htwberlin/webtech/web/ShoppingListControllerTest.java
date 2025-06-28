package de.htwberlin.webtech.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.htwberlin.webtech.persistence.ShoppingList;
import de.htwberlin.webtech.persistence.ShoppingListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ShoppingListController.class)
public class ShoppingListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllShoppingLists() throws Exception {
        ShoppingList list = new ShoppingList();
        list.setId(1L);
        list.setName("Wocheneinkauf");
        list.setPlannedDate(LocalDate.of(2025, 7, 1));

        when(shoppingListRepository.findAll()).thenReturn(List.of(list));

        mockMvc.perform(get("/api/lists"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Wocheneinkauf")));
    }

    @Test
    void shouldReturnShoppingListById() throws Exception {
        ShoppingList list = new ShoppingList();
        list.setId(1L);
        list.setName("KW30");

        when(shoppingListRepository.findById(1L)).thenReturn(Optional.of(list));

        mockMvc.perform(get("/api/lists/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("KW30")));
    }

    @Test
    void shouldCreateNewShoppingList() throws Exception {
        ShoppingList list = new ShoppingList();
        list.setId(1L);
        list.setName("Neue Liste");
        list.setPlannedDate(LocalDate.of(2025, 7, 5));
        list.setItems(List.of());

        when(shoppingListRepository.save(any())).thenReturn(list);

        mockMvc.perform(post("/api/lists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(list)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Neue Liste")))
                .andExpect(jsonPath("$.plannedDate", is("2025-07-05")));
    }

}
