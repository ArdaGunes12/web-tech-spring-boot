package de.htwberlin.webtech.web;

import de.htwberlin.webtech.persistence.ShoppingList;
import de.htwberlin.webtech.persistence.ShoppingListRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.ResponseEntity;


import java.util.List;

@RestController
@RequestMapping("/api/lists")
public class ShoppingListController {

    private final ShoppingListRepository repository;

    public ShoppingListController(ShoppingListRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ShoppingList> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public ShoppingList create(@RequestBody ShoppingList list) {
        return repository.save(list);
    }

    @GetMapping("/{id}")
    public ShoppingList getById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteList(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
