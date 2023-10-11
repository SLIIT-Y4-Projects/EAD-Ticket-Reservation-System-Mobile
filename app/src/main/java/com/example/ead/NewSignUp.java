package com.example.ead;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ead.StudentApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewSignUp extends AppCompatActivity {

    private static final String BASE_URL = "https://ticketreservationsystem01.azurewebsites.net/";

    private EditText userNameEditText; // Use this for both email and username
    private EditText passwordEditText;
    private Button signUpButton;
    private StudentApi registrationApi; // Your Retrofit API interface

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sign_up);

        // Initialize your UI elements
        userNameEditText = findViewById(R.id.userName); // Use this for both email and username
        passwordEditText = findViewById(R.id.password);


        // Create a Retrofit instance for API requests
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // Replace with your actual base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        registrationApi = retrofit.create(StudentApi.class); // Replace with your actual API interface
        signUpButton = findViewById(R.id.new_signup_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = userNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                Student student = new Student();
                student.setUsername(username);
                student.setPassword(password);

                // Make the registration API request
                Call<Void> call = registrationApi.register(student);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Registration successful
                            Toast.makeText(NewSignUp.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            // Redirect to the login page or perform any other necessary actions
                        } else {
                            // Registration failed
                            Toast.makeText(NewSignUp.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Handle API request failure
                        Toast.makeText(NewSignUp.this, "Failed to connect to the server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}

