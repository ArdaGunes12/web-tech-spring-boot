package de.htwberlin.webtech.web;

import de.htwberlin.webtech.persistence.Item;
import de.htwberlin.webtech.persistence.ItemRepository;
import de.htwberlin.webtech.persistence.ShoppingList;
import de.htwberlin.webtech.persistence.ShoppingListRepository;
import de.htwberlin.webtech.web.dto.ItemDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemRepository itemRepository;
    private final ShoppingListRepository shoppingListRepository;

    public ItemController(ItemRepository itemRepository, ShoppingListRepository shoppingListRepository) {
        this.itemRepository = itemRepository;
        this.shoppingListRepository = shoppingListRepository;
    }

    // GET: Alle Items als DTOs (ohne rekursive Liste)
    @GetMapping
    public List<ItemDto> getAll() {
        return itemRepository.findAll().stream()
                .map(ItemDto::fromEntity)
                .collect(Collectors.toList());
    }

    // GET: Einzelnes Item als DTO
    @GetMapping("/{id}")
    public ItemDto getById(@PathVariable Long id) {
        return itemRepository.findById(id)
                .map(ItemDto::fromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // POST: Neues Item mit ShoppingList-Id im DTO
    @PostMapping
    public ItemDto create(@RequestBody ItemDto itemDto) {
        ShoppingList list = shoppingListRepository.findById(itemDto.getShoppingListId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Liste nicht gefunden"));

        Item item = new Item(
                itemDto.getName(),
                itemDto.getCategory(),
                itemDto.getQuantity(),
                itemDto.isPurchased(),
                itemDto.getPrice()
        );
        item.setShoppingList(list);
        
        return ItemDto.fromEntity(itemRepository.save(item));
    }

    // PUT: Vorhandenes Item updaten (inkl. Einkaufsliste, falls gewünscht)
    @PutMapping("/{id}")
    public ItemDto update(@PathVariable Long id, @RequestBody ItemDto itemDto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        item.setName(itemDto.getName());
        item.setCategory(itemDto.getCategory());
        item.setQuantity(itemDto.getQuantity());
        item.setPurchased(itemDto.isPurchased());
        item.setPrice(itemDto.getPrice());

        if (itemDto.getShoppingListId() != null) {
            ShoppingList list = shoppingListRepository.findById(itemDto.getShoppingListId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Liste nicht gefunden"));
            item.setShoppingList(list);
        }

        return ItemDto.fromEntity(itemRepository.save(item));
    }

    // DELETE: Item löschen
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!itemRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        itemRepository.deleteById(id);
    }
}
