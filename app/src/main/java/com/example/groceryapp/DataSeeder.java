package com.example.groceryapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class DataSeeder {
    private static final String TAG = "DataSeeder";

    // List of grocery items to seed
    private static final List<GroceryItem> groceryItems = Arrays.asList(
            new GroceryItem("Apple", 10, 50, R.drawable.apple),
            new GroceryItem("Banana", 5, 30, R.drawable.banana),
            new GroceryItem("Orange", 9, 40, R.drawable.orange),
            new GroceryItem("Mango", 12, 20, R.drawable.mango),
            new GroceryItem("Grapes", 15, 25, R.drawable.grapes),
            new GroceryItem("Tomato", 7, 50, R.drawable.tomato),
            new GroceryItem("Potato", 3, 100, R.drawable.potato),
            new GroceryItem("Onion", 6, 70, R.drawable.onion),
            new GroceryItem("Harpic", 30, 15, R.drawable.harpic)
    );

    // Seed the database with initial grocery items
    public static void seedDatabase(DatabaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (!isDatabaseSeeded(db)) {
            for (GroceryItem item : groceryItems) {
                ContentValues values = new ContentValues();
                values.put("name", item.getName());
                values.put("price", item.getPrice());
                values.put("quantity", item.getQuantity());
                values.put("imageResId", item.getImageResId());
                db.insert("groceries", null, values);
            }
            Log.d(TAG, "Initial grocery items seeded.");
        } else {
            Log.d(TAG, "Database already seeded.");
        }
    }

    // Check if the groceries table is already seeded
    private static boolean isDatabaseSeeded(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM groceries", null);
        boolean hasData = false;
        if (cursor.moveToFirst()) {
            hasData = cursor.getInt(0) > 0;
        }
        cursor.close();
        return hasData;
    }
}
