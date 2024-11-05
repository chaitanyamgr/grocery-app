package com.example.groceryapp.Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.groceryapp.Database.DatabaseHelper;
import com.example.groceryapp.R;

public class SignupActivity extends AppCompatActivity {
    private EditText nameEditText, emailEditText, passwordEditText, phone;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup); // Use your layout file name

        // Initialize views
        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.signup_email);
        passwordEditText = findViewById(R.id.signup_password);
        phone = findViewById(R.id.phoneNo);
        Button signupButton = findViewById(R.id.signup_button);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Set up signup button click listener
        signupButton.setOnClickListener(v -> handleSignup());
    }

    private void handleSignup() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String phoneNo = phone.getText().toString().trim();


        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(phoneNo)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phoneNo.length()<10) {
            Toast.makeText(this, "Enter Valid Phone No.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add user to the database
        boolean isAdded = databaseHelper.addUser(name, email, password, phoneNo); // You can add address if needed

        if (isAdded) {
            Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show();

            // Create an Intent to redirect to the LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "User registration failed", Toast.LENGTH_SHORT).show();
        }

    }
}
