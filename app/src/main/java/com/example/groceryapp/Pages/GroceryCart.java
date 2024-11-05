package com.example.groceryapp.Pages;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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
    private Button pay_now_button;
    private TextView totalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocery_cart);
        totalTextView = findViewById(R.id.total_amount);
        cartRecyclerView = findViewById(R.id.cart_recycler_view);
        pay_now_button = findViewById(R.id.pay_now_button);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        String name = getIntent().getStringExtra("name");
        // Assume you get this list from the previous activity
        cartItems = (ArrayList<CartItem>) getIntent().getSerializableExtra("cartItems");
        cartAdapter = new CartAdapter(this, cartItems);
        cartRecyclerView.setAdapter(cartAdapter);
        cartAdapter.setTotalUpdateListener(newTotal -> {
            totalTextView.setText(String.format("Total: ₹%d", newTotal));
        });
        pay_now_button.setOnClickListener(v->{
            Intent intent = new Intent(this, OrderConfirmationActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
            finish();
        });
        cartAdapter.calculateInitialTotal();
    }
}

