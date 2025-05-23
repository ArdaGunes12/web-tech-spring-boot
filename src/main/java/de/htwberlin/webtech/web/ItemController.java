package de.htwberlin.webtech.web;

import de.htwberlin.webtech.persistence.Item;
import de.htwberlin.webtech.persistence.ItemRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemRepository repository;

    public ItemController(ItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Item> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Item create(@RequestBody Item item) {
        return repository.save(item);
    }
}
