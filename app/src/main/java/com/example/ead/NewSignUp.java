package com.example.ead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ead.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.ead.databinding.ActivityNewSignUpBinding;
import com.example.ead.databinding.ActivitySignupBinding;

public class NewSignUp extends AppCompatActivity {

    private static final String BASE_URL = "https://ticketreservationsystemapinew.azurewebsites.net/";

    private EditText userFullNameEditText;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private Button signUpButton;
    private UserApi registrationApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sign_up);


        userNameEditText = findViewById(R.id.userName);
        passwordEditText = findViewById(R.id.password);
        userFullNameEditText = findViewById(R.id.fullName);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        registrationApi = retrofit.create(UserApi.class);
        signUpButton = findViewById(R.id.new_signup_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = userNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String fullName = userFullNameEditText.getText().toString();

                Traveller traveller = new Traveller();

                traveller.setUsername(username);
                traveller.setPassword(password);
                traveller.setFullName(fullName);


                Call<Void> call = registrationApi.register(traveller);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {

                            Toast.makeText(NewSignUp.this, "Registration successful", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(NewSignUp.this, UserHome.class);
                            startActivity(intent);
                            finish();


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

