package com.example.groceryapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GroceryActivity extends AppCompatActivity {

    private int totalAmount = 0;
    private TextView totalTextView, userDetailsTextView;
    private DatabaseHelper databaseHelper;
    private List<GroceryItem> groceryItems; // List to store grocery items

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);

        // Initialize DatabaseHelper and seed data
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.getWritableDatabase(); // Ensure database is created
        DataSeeder.seedDatabase(databaseHelper); // Seed data

        // Get the passed name and phone from login page
        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");

        // Initialize UI components
        userDetailsTextView = findViewById(R.id.user_details);
        totalTextView = findViewById(R.id.total_amount);

        // Set user details
        userDetailsTextView.setText("Hello, " + name + " (" + phone + ")");

        // Retrieve grocery items from database
        groceryItems = getGroceryItems();

        GridView gridView = findViewById(R.id.grid_view);
        GroceryAdapter adapter = new GroceryAdapter(this, groceryItems); // Pass groceryItems to the adapter
        gridView.setAdapter(adapter);

        // Handle item clicks
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            GroceryItem selectedItem = groceryItems.get(position);
            totalAmount += selectedItem.getPrice(); // Add item price to total
            updateUI();
            Toast.makeText(GroceryActivity.this, selectedItem.getName() + " added to cart!", Toast.LENGTH_SHORT).show();
        });

        // Handle Pay button click
        findViewById(R.id.pay_button).setOnClickListener(v ->
                Toast.makeText(GroceryActivity.this, "Total Payable Amount: ₹" + totalAmount, Toast.LENGTH_SHORT).show()
        );
    }

    // Method to retrieve grocery items from the database
    private List<GroceryItem> getGroceryItems() {
        List<GroceryItem> items = new ArrayList<>();

        // Get the cursor from the DatabaseHelper
        Cursor cursor = databaseHelper.getAllGroceryItems();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex(GroceryItemsTable.COLUMN_GROCERY_NAME));
                    int price = cursor.getInt(cursor.getColumnIndex(GroceryItemsTable.COLUMN_PRICE));
                    int quantity = cursor.getInt(cursor.getColumnIndex(GroceryItemsTable.COLUMN_QUANTITY));
                    int imageResId = cursor.getInt(cursor.getColumnIndex(GroceryItemsTable.COLUMN_IMAGE_RES_ID));

                    // Create a new GroceryItem and add it to the list
                    items.add(new GroceryItem(name, price, quantity, imageResId));
                } while (cursor.moveToNext());
            }
            cursor.close(); // Make sure to close the cursor to avoid memory leaks
        } else {
            Log.e("GroceryActivity", "Cursor is null, no data retrieved from the database.");
        }

        return items;
    }


    // Update total amount displayed
    private void updateUI() {
        totalTextView.setText("Total: ₹" + totalAmount);
    }
}
