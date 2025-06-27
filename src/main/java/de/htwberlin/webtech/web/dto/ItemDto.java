package de.htwberlin.webtech.web.dto;

import de.htwberlin.webtech.persistence.Item;

public class ItemDto {

    private Long id;
    private String name;
    private String category;
    private int quantity;
    private boolean purchased;
    private double price;
    private Long shoppingListId;  // Nur die ID der Liste, nicht das ganze Objekt

    // Konstruktor
    public ItemDto(Long id, String name, String category, int quantity, boolean purchased, double price, Long shoppingListId) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.purchased = purchased;
        this.price = price;
        this.shoppingListId = shoppingListId;
    }

    // Getter
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public double getPrice() {
        return price;
    }

    public Long getShoppingListId() {
        return shoppingListId;
    }

    // Mapping von Entity zu DTO
    public static ItemDto fromEntity(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getCategory(),
                item.getQuantity(),
                item.isPurchased(),
                item.getPrice(),
                item.getShoppingList() != null ? item.getShoppingList().getId() : null
        );
    }
}
