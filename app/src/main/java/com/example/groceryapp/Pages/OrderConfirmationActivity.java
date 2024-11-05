package com.example.groceryapp.Pages;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.groceryapp.R;

public class OrderConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        String name = getIntent().getStringExtra("name");
        // Initialize views
        Button goToHomeButton = findViewById(R.id.go_to_home_button);

        // Set up the button click listener
        goToHomeButton.setOnClickListener(v -> {
            // Navigate to the Home Page
            Intent intent = new Intent(OrderConfirmationActivity.this, GroceryActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("name", name);
            startActivity(intent);
            finish();
        });
    }
}
