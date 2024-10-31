package com.example.groceryapp.Models;

public class GroceryItem {
    private final String name;
    private final int price;
    private final int quantity;
    private final int imageResId;

    public GroceryItem(String name, int price, int quantity, int imageResId) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getImageResId() {
        return imageResId;
    }
}