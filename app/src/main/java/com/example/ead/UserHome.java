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
import android.widget.Toast;
import java.util.ResourceBundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserHome extends AppCompatActivity {

    private static final String BASE_URL = "https://ticketreservationsystemapinew.azurewebsites.net/";
    private Button updateButton;
    private Button DeactivatedButton;
    private Button logoutButton;
    private EditText userName;
    private String userID;
    private UserApi logoutApi;
    private UserApi deactivateApi;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public void onViewTrainViewButtonClick(View view) {
        Intent loginIntent = new Intent(this, TrainViewActivity.class);
        startActivity(loginIntent);
    }

    public void onViewReservationButtonClick(View view) {
        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
    }

    DataBaseHelper dataBaseHelper = new DataBaseHelper(this) ;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userID = sharedPreferences.getString("user_id", null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        deactivateApi = retrofit.create(UserApi.class);


    }
    public void onLogoutButtonClick(View view) {

        Toast.makeText(UserHome.this, userID, Toast.LENGTH_SHORT).show();
        Boolean isDeleted=dataBaseHelper.deleteLoginByID(userID);
        if(isDeleted){
            Toast.makeText(UserHome.this, "Session Destroyed", Toast.LENGTH_SHORT).show();

    }else{
        Toast.makeText(UserHome.this, "Logout Failed", Toast.LENGTH_SHORT).show();

    }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();




    }
    public void onDeactivateButtonClick(View view) {

        Call<Void> call = deactivateApi.userDeactivate(userID);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {

                    showToast("User deactivated successfully");

                    Intent intent = new Intent(UserHome.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    showToast("Error: Failed to deactivate user.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle the failure (e.g., show an error message)
                showToast("Error: " + t.getMessage());
            }
        });



    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}