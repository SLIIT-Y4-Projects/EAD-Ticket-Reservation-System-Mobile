package com.example.ead;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.ead.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding; // Use the generated binding class
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize your DataBaseHelper here
        dataBaseHelper = new DataBaseHelper(this);
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();
                String confirmPassword = binding.cnfPassword.getText().toString();
                String nic = binding.nicNo.getText().toString(); // Get the NIC number from the input field

                if (email.equals("") || password.equals("") || confirmPassword.equals("") || nic.equals("")) {
                    Toast.makeText(SignupActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.equals(confirmPassword)) {
                        // Check if NIC is valid
                        if (!dataBaseHelper.isNICValid(nic)) {
                            Toast.makeText(SignupActivity.this, "Enter valid NIC number", Toast.LENGTH_SHORT).show();
                        } else {
                            // Check if NIC already exists in the database
                            Boolean checkUserEmail = dataBaseHelper.checkEmail(email);

                            if (checkUserEmail) {
                                Toast.makeText(SignupActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
                            } else {
                                // Check if NIC already exists in the database
                                Boolean checkNIC = dataBaseHelper.checkNIC(nic);

                                if (checkNIC) {
                                    Toast.makeText(SignupActivity.this, "NIC number already exists", Toast.LENGTH_SHORT).show();
                                } else {
                                    Boolean isInserted = dataBaseHelper.insertData(nic, email, password);
                                    if (isInserted) {
                                        Toast.makeText(SignupActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(SignupActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    } else {
                        Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
        });
    }
}
