package com.example.groceryapp.Pages;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.groceryapp.Models.CartItem;
import com.example.groceryapp.R;

import java.util.ArrayList;
public class GroceryCart extends AppCompatActivity {

    private TextView cartDetailsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocery_cart);

        // Initialize UI components
        cartDetailsTextView = findViewById(R.id.cart_details);

        // Retrieve cart items from Intent
        ArrayList<CartItem> cartItems = (ArrayList<CartItem>) getIntent().getSerializableExtra("cartItems");

        if (cartItems != null && !cartItems.isEmpty()) {
            StringBuilder details = new StringBuilder();
            int totalAmount = 0;

            // Display cart items and calculate total
            for (CartItem item : cartItems) {
                details.append(item.getName()).append(" - ₹").append(item.getPrice())
                        .append(" x ").append(item.getQuantity()).append("\n");
                totalAmount += item.getPrice() * item.getQuantity();
            }

            details.append("\nTotal Amount: ₹").append(totalAmount);
            cartDetailsTextView.setText(details.toString());
        } else {
            cartDetailsTextView.setText("No items in cart.");
        }
    }
}

