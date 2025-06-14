package de.htwberlin.webtech.web;

import de.htwberlin.webtech.persistence.Item;
import de.htwberlin.webtech.persistence.ItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/{id}")
    public Item getById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public Item create(@RequestBody Item item) {
        return repository.save(item);
    }


    @PutMapping("/{id}")
    public Item update(@PathVariable Long id, @RequestBody Item updatedItem) {
        return repository.findById(id)
                .map(item -> {
                    item.setName(updatedItem.getName());
                    item.setCategory(updatedItem.getCategory());
                    item.setQuantity(updatedItem.getQuantity());
                    item.setPurchased(updatedItem.isPurchased());
                    return repository.save(item);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
    }


}
