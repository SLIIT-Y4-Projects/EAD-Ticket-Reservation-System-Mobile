package com.example.ead;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class New_Login extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;

    private static final String BASE_URL = "https://ticketreservationsystem01.azurewebsites.net/";

    private StudentApi loginApi;
    private EditText loginUserName;
    private EditText loginPassword;
    private Button loginButton;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);


        loginUserName = findViewById(R.id.loginUserName);
        loginPassword = findViewById(R.id.loginPassword);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loginApi = retrofit.create(StudentApi.class);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = loginUserName.getText().toString();
                String password = loginPassword.getText().toString();

                Student student = new Student();
                student.setUsername(username);
                student.setPassword(password);

                if (username.equals("") || password.equals("")) {
                    Toast.makeText(New_Login.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    // Make the login API request
                    Call<Void> call = loginApi.login(student);
                    call.enqueue(new Callback<Void>() {
                        @Override

                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {

                                Toast.makeText(New_Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                String authToken = dataBaseHelper.getAuthTokenByUsername(username);

                                if (authToken != null) {

                                    Intent intent = new Intent(getApplicationContext(), Student_add.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(New_Login.this, "Failed to retrieve authentication token", Toast.LENGTH_SHORT).show();
                                }} else {

                                Toast.makeText(New_Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            // Handle API request failure
                            Toast.makeText(New_Login.this, "Failed to connect to the server", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }
}
