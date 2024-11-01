package com.example.groceryapp.Pages;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.groceryapp.Database.DataSeeder;
import com.example.groceryapp.Database.DatabaseHelper;
import com.example.groceryapp.R;

public class MainActivity extends AppCompatActivity {

    private Button getStartedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing); // Start with the login page

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.getWritableDatabase(); // Ensure database is created
        DataSeeder.seedDatabase(databaseHelper); // Seed data

        getStartedBtn = findViewById(R.id.get_started_button);
        getStartedBtn.setOnClickListener(v -> {
            // Redirect to the LoginActivity when the button is clicked
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}