package com.example.groceryapp.Pages;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceryapp.Adapters.CartAdapter;
import com.example.groceryapp.Models.CartItem;
import com.example.groceryapp.R;

import java.util.ArrayList;
import java.util.List;

public class GroceryCart extends AppCompatActivity {
    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;
    private TextView totalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocery_cart);
        totalTextView = findViewById(R.id.total_amount);
        cartRecyclerView = findViewById(R.id.cart_recycler_view);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Assume you get this list from the previous activity
        cartItems = (ArrayList<CartItem>) getIntent().getSerializableExtra("cartItems");
        cartAdapter = new CartAdapter(this, cartItems);
        cartRecyclerView.setAdapter(cartAdapter);
        cartAdapter.setTotalUpdateListener(newTotal -> {
            totalTextView.setText(String.format("Total: ₹%d", newTotal));
        });
        cartAdapter.calculateInitialTotal();
    }
}

