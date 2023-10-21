package com.example.ead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ead.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.ead.databinding.ActivityNewLoginBinding;

public class New_Login extends AppCompatActivity {
    ActivityNewLoginBinding binding;

    DataBaseHelper dataBaseHelper = new DataBaseHelper(this) ;


    private static final String BASE_URL = "https://ticketreservationsystemapinew.azurewebsites.net/";

    private UserApi loginApi;
    private EditText loginUserName;
    private EditText loginPassword;
    private Button loginButton;


    public void onCreate(SQLiteDatabase db) {
        Log.d("Database", "onCreate() called");
        db.execSQL("CREATE TABLE login(id TEXT PRIMARY KEY, token TEXT)");
    }

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);
        binding = ActivityNewLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginUserName = findViewById(R.id.loginUserName);
        loginPassword = findViewById(R.id.loginPassword);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loginApi = retrofit.create(UserApi.class);
        loginButton = findViewById(R.id.loginButton);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = loginUserName.getText().toString();
                String password = loginPassword.getText().toString();

                Traveller traveller = new Traveller();
                traveller.setUsername(username);
                traveller.setPassword(password);

                if (username.equals("") || password.equals("")) {
                    Toast.makeText(New_Login.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    // Make the login API request
                    Call<AuthTokenResponse> call = loginApi.login(traveller);
                    call.enqueue(new Callback<AuthTokenResponse>() {
                        @Override

                        public void onResponse(Call<AuthTokenResponse> call, Response<AuthTokenResponse> response) {
                            Log.e("login Test", "test test: ");


                            if(response.isSuccessful()) {
                                String Token =response.body().getToken();
                                String ID = response.body().getId();


                                Log.d("TAG", Token);
                                Log.d("TAG", ID);

                                editor.putString("user_id", ID);
                                editor.apply();
                                String userID = sharedPreferences.getString("user_id", null);
                                Log.d("TAG", userID);


                                Boolean isInserted=dataBaseHelper.insertLogin(ID,Token);
                                if(isInserted){
                                    Toast.makeText(New_Login.this, "Registration successful SQL", Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(New_Login.this, Token, Toast.LENGTH_SHORT).show();
                                Toast.makeText(New_Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(New_Login.this, UserHome.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(New_Login.this, "Login Failed", Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<AuthTokenResponse> call, Throwable t) {
                            // Handle API request failure
                            Toast.makeText(New_Login.this, "Failed to connect to the server", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }
}
