package de.htwberlin.webtech.persistence;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class ShoppingList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate plannedDate;

    @OneToMany(mappedBy = "shoppingList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;

    // Getter & Setter
    public Long getId() { return id; }
    public String getName() { return name; }
    public LocalDate getPlannedDate() { return plannedDate; }
    public List<Item> getItems() { return items; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPlannedDate(LocalDate plannedDate) { this.plannedDate = plannedDate; }
    public void setItems(List<Item> items) { this.items = items; }

}
