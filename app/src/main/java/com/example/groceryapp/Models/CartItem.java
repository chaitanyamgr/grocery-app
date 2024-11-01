package com.example.groceryapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class CartItem implements Parcelable {
    private String name;
    private int price;
    private int quantity;
    private int imageResourceId; // Declare the imageResourceId

    // Constructor
    public CartItem(int imageResourceId, int quantity, int price, String name) {
        this.imageResourceId = imageResourceId; // Initialize imageResourceId
        this.quantity = quantity;
        this.price = price;
        this.name = name;
    }

    // Parcelable implementation
    protected CartItem(Parcel in) {
        name = in.readString();
        price = in.readInt();
        quantity = in.readInt();
        imageResourceId = in.readInt(); // Read imageResourceId from the parcel
    }

    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(price);
        dest.writeInt(quantity);
        dest.writeInt(imageResourceId); // Write imageResourceId to the parcel
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getImageResourceId() { // Add this getter
        return imageResourceId;
    }
}
