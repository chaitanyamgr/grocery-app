package com.example.groceryapp.Database;

import static com.example.groceryapp.Database.GroceryItemsTable.COLUMN_GROCERY_ID;
import static com.example.groceryapp.Database.GroceryItemsTable.COLUMN_GROCERY_NAME;
import static com.example.groceryapp.Database.GroceryItemsTable.COLUMN_IMAGE_RES_ID;
import static com.example.groceryapp.Database.GroceryItemsTable.COLUMN_PRICE;
import static com.example.groceryapp.Database.GroceryItemsTable.COLUMN_QUANTITY;
import static com.example.groceryapp.Database.GroceryItemsTable.TABLE_GROCERIES;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "GroceryApp.db";
    private static final int DATABASE_VERSION = 3; // Incremented version

    // Users Table
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_PHONE = "phone"; // Optional field

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "DatabaseHelper instantiated");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users Table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT UNIQUE, " + // Ensure email is unique
                COLUMN_PASSWORD + " TEXT, " + // Store hashed password if necessary
                COLUMN_PHONE + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);
        Log.d(TAG, "Users table created: " + TABLE_USERS);

        // Create Groceries Table
        String CREATE_GROCERIES_TABLE = "CREATE TABLE " + TABLE_GROCERIES + " (" +
                COLUMN_GROCERY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_GROCERY_NAME + " TEXT, " +
                COLUMN_PRICE + " INTEGER, " +
                COLUMN_QUANTITY + " INTEGER, " +
                COLUMN_IMAGE_RES_ID + " INTEGER)";
        db.execSQL(CREATE_GROCERIES_TABLE);
        Log.d(TAG, "Groceries table created: " + TABLE_GROCERIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROCERIES);
        onCreate(db);
        Log.d(TAG, "Database upgraded to version " + newVersion);
    }

    // User Methods
    public boolean addUser(String name, String email, String password, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password); // Store the hashed password if you implement hashing
        values.put(COLUMN_PHONE, phone);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        if (result == -1) {
            Log.e(TAG, "Failed to insert user: " + name);
            return false;
        } else {
            Log.d(TAG, "User added: " + name);
            return true;
        }
    }

    public String getUserName(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_NAME + " FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                cursor.close();
                return name; // Return the user's name
            }
            cursor.close();
        }
        return null; // Return null if no user is found
    }


    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        Log.d(TAG, "Retrieved " + cursor.getCount() + " users from database");
        return cursor;
    }

    // Check if user exists with email and password
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " +
                COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{email, password});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    // Grocery Methods
    public boolean addGroceryItem(String name, int price, int quantity, int imageResId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_GROCERY_NAME, name);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_IMAGE_RES_ID, imageResId);

        long result = db.insert(TABLE_GROCERIES, null, values);
        db.close();
        if (result == -1) {
            Log.e(TAG, "Failed to insert grocery item: " + name);
            return false;
        } else {
            Log.d(TAG, "Grocery item added: " + name);
            return true;
        }
    }

    public Cursor getAllGroceryItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GROCERIES, null);
        Log.d(TAG, "Retrieved " + cursor.getCount() + " grocery items from database");
        return cursor;
    }
}
