package com.example.groceryapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GroceryActivity extends AppCompatActivity {

    private int totalAmount = 0;
    private TextView totalTextView, userDetailsTextView;

    // Grocery items and prices
    private String[] items = {"Apple", "Banana", "Orange", "Mango", "Grapes", "Tomato", "Potato", "Onion","Harpic"};
    private int[] prices = {10, 5, 9, 12, 15, 7, 3, 6, 30};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery); // Load the grocery page layout

        // Get the passed name and phone from login page
        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");

        // Initialize UI components
        userDetailsTextView = findViewById(R.id.user_details);
        totalTextView = findViewById(R.id.total_amount);

        // Set user details
        userDetailsTextView.setText("Hello, " + name + " (" + phone + ")");

        GridView gridView = findViewById(R.id.grid_view);
        GroceryAdapter adapter = new GroceryAdapter(this, items, prices);
        gridView.setAdapter(adapter);

        // Handle item clicks
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                totalAmount += prices[position]; // Add item price to total
                updateUI();
                Toast.makeText(GroceryActivity.this, items[position] + " added to cart!", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle Pay button click
        findViewById(R.id.pay_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GroceryActivity.this, "Total Payable Amount: ₹" + totalAmount, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Update total amount displayed
    private void updateUI() {
        totalTextView.setText("Total: ₹" + totalAmount);
    }
}
