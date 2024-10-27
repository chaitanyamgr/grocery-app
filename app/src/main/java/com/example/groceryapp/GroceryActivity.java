package com.example.groceryapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GroceryItem selectedItem = groceryItems.get(position);
                totalAmount += selectedItem.getPrice(); // Add item price to total
                updateUI();
                Toast.makeText(GroceryActivity.this, selectedItem.getName() + " added to cart!", Toast.LENGTH_SHORT).show();
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

    // Method to retrieve grocery items from the database
    private List<GroceryItem> getGroceryItems() {
        List<GroceryItem> items = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM groceries", null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int price = cursor.getInt(cursor.getColumnIndex("price"));
                int quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
                int imageResId = cursor.getInt(cursor.getColumnIndex("imageResId"));
                items.add(new GroceryItem(name, price, quantity, imageResId));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    // Update total amount displayed
    private void updateUI() {
        totalTextView.setText("Total: ₹" + totalAmount);
    }
}
