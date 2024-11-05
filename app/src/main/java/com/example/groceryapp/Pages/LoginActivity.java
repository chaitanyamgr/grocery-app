package com.example.groceryapp.Pages;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.groceryapp.Database.DatabaseHelper;
import com.example.groceryapp.R;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button loginButton;
    private DatabaseHelper databaseHelper;
    private TextView signUp,forgotText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Set the layout for the login page
        databaseHelper = new DatabaseHelper(this);

        // Initialize login UI components
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        signUp = findViewById(R.id.signup);
        forgotText =findViewById(R.id.forgot_password);


        // Handle login button click
        loginButton.setOnClickListener(v -> {
            String userEmailId = email.getText().toString().trim();
            String userPassword = password.getText().toString().trim();

            if (userEmailId.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
            } else {
                // Check if the user exists in the database
                boolean isUserValid = databaseHelper.checkUser(userEmailId, userPassword);
                if (isUserValid) {
                    // Fetch the user's name
                    String userName = databaseHelper.getUserName(userEmailId);
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

                    // Proceed to GroceryActivity
                    Intent intent = new Intent(this, GroceryActivity.class);
                    intent.putExtra("email", userEmailId); // Pass email if needed in GroceryActivity
                    intent.putExtra("name", userName);
                    startActivity(intent);
                    finish(); // Close LoginActivity
                } else {
                    Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUp.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
            finish();
        });

        forgotText.setOnClickListener(v -> {
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            startActivity(intent);
            finish();
        });
    }
}