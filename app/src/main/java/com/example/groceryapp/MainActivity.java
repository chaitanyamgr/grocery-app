package com.example.groceryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText, phoneEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Start with the login page

        // Initialize login UI components
        nameEditText = findViewById(R.id.name_input);
        phoneEditText = findViewById(R.id.phone_input);
        loginButton = findViewById(R.id.login_button);

        // Handle login button click
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();

                if (name.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter your name and phone number", Toast.LENGTH_SHORT).show();
                } else {
                    // Proceed to the GroceryApp page with name and phone
                    Intent intent = new Intent(MainActivity.this, GroceryActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("phone", phone);
                    startActivity(intent);
                    finish(); // Close the login activity
                }
            }
        });
    }
}
