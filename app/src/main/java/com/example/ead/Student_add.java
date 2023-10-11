package com.example.ead;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Student_add extends AppCompatActivity {
    private TextView tokenTextView;

    public void onLoginButtonClick(View view) {
        Intent loginIntent = new Intent(this, LoginActivity.class); // Replace LoginActivity.class with the actual login activity class
        startActivity(loginIntent);
    }

    // Method to handle the Sign-Up button click
    public void onSignUpButtonClick(View view) {
        Intent signUpIntent = new Intent(this, SignupActivity.class); // Replace SignUpActivity.class with the actual sign-up activity class
        startActivity(signUpIntent);
    }

    public void onViewButtonClick(View view) {
        Intent signUpIntent = new Intent(this, MainActivity.class); // Replace SignUpActivity.class with the actual sign-up activity class
        startActivity(signUpIntent);
    }
    public void onNewSignUPButtonClick(View view) {
        Intent signUpIntent = new Intent(this, NewSignUp.class); // Replace SignUpActivity.class with the actual sign-up activity class
        startActivity(signUpIntent);
    }

    public void onNewLoginButtonClick(View view) {
        Intent loginIntent = new Intent(this, New_Login.class); // Replace SignUpActivity.class with the actual sign-up activity class
        startActivity(loginIntent);
    }
    private static final String BASE_URL = "https://studentmanagement20231009140446.azurewebsites.net/";
    // Replace with your API base URL
    private EditText etName, etAge,etGender; // Reference to your EditText fields
    private StudentAddApi studentAddApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add);

        tokenTextView = findViewById(R.id.tokenTextView);
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etGender = findViewById(R.id.etGender);


// Create a Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

// Create an instance of the StudentAddApi interface
        studentAddApi = retrofit.create(StudentAddApi.class);

        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve student data from EditText fields
                String name = etName.getText().toString();
                int age = Integer.parseInt(etAge.getText().toString());
                String gender = etGender.getText().toString();


                // Create a Student object with the data
                Student student = new Student();
                student.setName(name);
                student.setAge(age);
                student.setGender(gender);

                // Add the course to the list of courses

                // Send the POST request
                Call<StudentResponse> call = studentAddApi.addStudent(student);
                call.enqueue(new Callback<StudentResponse>() {
                    @Override
                    public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {
                        if (response.isSuccessful()) {
                            // Handle a successful response (e.g., show a success message)
                            StudentResponse studentResponse = response.body();
                            String message = studentResponse.getMessage();
                            showToast("Success: " + message);

                            // Display a success message or navigate back, etc.

                            Intent intent = new Intent(Student_add.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Handle an error response (e.g., show an error message)
                            // You can access the error response using response.errorBody()
                            showToast("Error: Failed to add student.");
                        }
                    }

                    @Override
                    public void onFailure(Call<StudentResponse> call, Throwable t) {
                        // Handle the failure (e.g., show an error message)
                        showToast("Error: " + t.getMessage());
                    }
                });
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
