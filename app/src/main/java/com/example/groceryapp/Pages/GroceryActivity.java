package com.example.groceryapp.Pages;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.groceryapp.Adapters.GroceryItemAdapter;
import com.example.groceryapp.Database.DatabaseHelper;
import com.example.groceryapp.Database.GroceryItemsTable;
import com.example.groceryapp.Models.CartItem;
import com.example.groceryapp.Models.GroceryItem;
import com.example.groceryapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroceryActivity extends AppCompatActivity implements GroceryItemAdapter.OnItemAddedToCartListener {

    private TextView totalTextView;
    private DatabaseHelper databaseHelper;
    private GroceryItemAdapter groceryAdapter;
    private int totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);

        // Initialize UI components
        TextView userDetailsTextView = findViewById(R.id.user_details);
        totalTextView = findViewById(R.id.total_amount);

        // Get user data from Intent
        String name = getIntent().getStringExtra("name");
        userDetailsTextView.setText(String.format("%s %s", getString(R.string.hello), name));

        // Initialize DatabaseHelper and fetch data
        databaseHelper = new DatabaseHelper(this);
        List<GroceryItem> groceryItems = getGroceryItems();

        // Set up GridView with the adapter
        GridView gridView = findViewById(R.id.grid_view);
        groceryAdapter = new GroceryItemAdapter(this, groceryItems, this); // Pass the listener
        gridView.setAdapter(groceryAdapter);

        // Handle Pay button click
        Button cartButton = findViewById(R.id.cart_button);
        cartButton.setOnClickListener(v -> {
            int totalAmountToPay = calculateTotalAmount(groceryAdapter.getCartItems()); // Get total from cart items
            totalTextView.setText(String.format("Total: ₹%d", totalAmountToPay)); // Update the total amount TextView

            // Create an intent to start the PaymentActivity
            Intent intent = new Intent(GroceryActivity.this, GroceryCart.class);
            intent.putExtra("cartItems", new ArrayList<>(groceryAdapter.getCartItems().values())); // Pass cart items
            startActivity(intent);
            Toast.makeText(GroceryActivity.this, "Total Payable Amount: ₹" + totalAmountToPay, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onItemAdded(GroceryItem item) {
        // Update total amount when an item is added
        totalAmount += item.getPrice(); // Update the total amount
        totalTextView.setText(String.format("Total: ₹%d", totalAmount)); // Update the total amount TextView
        Log.d("Total", String.valueOf(totalAmount));
    }

    // Retrieve grocery items from database
    private List<GroceryItem> getGroceryItems() {
        List<GroceryItem> items = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllGroceryItems();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex(GroceryItemsTable.COLUMN_GROCERY_NAME));
                    int price = cursor.getInt(cursor.getColumnIndex(GroceryItemsTable.COLUMN_PRICE));
                    int quantity = cursor.getInt(cursor.getColumnIndex(GroceryItemsTable.COLUMN_QUANTITY));
                    int imageResId = cursor.getInt(cursor.getColumnIndex(GroceryItemsTable.COLUMN_IMAGE_RES_ID));
                    items.add(new GroceryItem(name, price, quantity, imageResId));
                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {
            Log.e("GroceryActivity", "Cursor is null, no data retrieved from the database.");
        }

        return items;
    }

    // Calculate total amount from cart items
    private int calculateTotalAmount(Map<String, CartItem> cartItems) {
        int total = 0;
        for (CartItem item : cartItems.values()) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
}