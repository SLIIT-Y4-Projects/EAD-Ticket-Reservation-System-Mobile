package com.example.ead;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrainViewActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://ticketreservationsystemapinew.azurewebsites.net/";

    private static final String TAG = "TrainViewActivity";
    private RecyclerView recyclerView;

    private TrainAdapter trainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_view);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        trainAdapter = new TrainAdapter(this);
        recyclerView.setAdapter(trainAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserApi trainApi = retrofit.create(UserApi.class);

        Call<List<Train>> call = trainApi.getTrain();
        call.enqueue(new Callback<List<Train>>() {
            @Override
            public void onResponse(Call<List<Train>> call, Response<List<Train>> response) {
                if (response.isSuccessful()) {
                    List<Train> trains = response.body();
                    trainAdapter.setTrainList(trains); // Changed "adapter" to "trainAdapter"
                } else {
                    Log.e(TAG, "API call failed: " + response.message());
                    showToast("Failed to fetch train data. Please check your network connection.");
                }
            }

            @Override
            public void onFailure(Call<List<Train>> call, Throwable t) {
                // Handle failure and provide user feedback
                Log.e(TAG, "API call failed: " + t.getMessage());
                showToast("Failed to fetch train data. Please check your network connection.");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
