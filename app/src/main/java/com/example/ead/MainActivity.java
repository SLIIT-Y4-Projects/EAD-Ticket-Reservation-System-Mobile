package com.example.ead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;




import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://ticketreservationsystem01.azurewebsites.net/";
    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentAdapter(this);
        recyclerView.setAdapter(adapter);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        UserApi studentApi = retrofit.create(UserApi.class);


        Call<List<Reservation>> call = studentApi.getReservation();
        call.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                if (response.isSuccessful()) {

                    List<Reservation> reservations = response.body();

                    adapter.setStudentList(reservations);
                } else {

                    Log.e(TAG, "API call failed: " + response.message());
                    showToast("Failed to fetch student data. Please check your network connection.");
                }
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                // Handle failure and provide user feedback
                Log.e(TAG, "API call failed: " + t.getMessage());
                showToast("Failed to fetch student data. Please check your network connection.");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

