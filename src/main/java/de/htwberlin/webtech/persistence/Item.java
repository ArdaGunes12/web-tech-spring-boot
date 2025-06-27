package de.htwberlin.webtech.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private int quantity;
    private boolean purchased;
    private Double price;

    public Item() {
    }

    public Item(String name, String category, int quantity, boolean purchased, Double price) {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.purchased = purchased;
        this.price = price;
    }

    // Getter und Setter
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public boolean isPurchased() { return purchased; }
    public Double getPrice() {return price; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPurchased(boolean purchased) { this.purchased = purchased; }
    public void setPrice(Double price) {this.price = price; }


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "shopping_list_id")
    private ShoppingList shoppingList;

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

}


