package com.example.groceryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "GroceryApp.db";
    private static final int DATABASE_VERSION = 2; // Incremented version

    // Users Table
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";

    // Grocery Items Table
    private static final String TABLE_GROCERIES = "groceries";
    private static final String COLUMN_GROCERY_ID = "id";
    private static final String COLUMN_GROCERY_NAME = "name";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_IMAGE_RES_ID = "imageResId";

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
    public boolean addUser(String name, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
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

    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        Log.d(TAG, "Retrieved " + cursor.getCount() + " users from database");
        return cursor;
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