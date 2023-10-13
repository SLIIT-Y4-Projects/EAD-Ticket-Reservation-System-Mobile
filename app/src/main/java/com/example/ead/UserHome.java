package com.example.ead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ResourceBundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserHome extends AppCompatActivity {

    private static final String BASE_URL = "https://ticketreservationsystem01.azurewebsites.net/";
    private Button updateButton;
    private Button DeactivatedButton;
    private Button logoutButton;
    private EditText userName;
    private String userID;
    private UserApi logoutApi;

    DataBaseHelper dataBaseHelper = new DataBaseHelper(this) ;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("user_id", null);


    }
    public void onLogoutButtonClick(View view) {

        Toast.makeText(UserHome.this, userID, Toast.LENGTH_SHORT).show();
        Boolean isDeleted=dataBaseHelper.deleteLoginByID(userID);
        if(isDeleted){
            Toast.makeText(UserHome.this, "session deleted", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(UserHome.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UserHome.this, New_Login.class);





    }
}