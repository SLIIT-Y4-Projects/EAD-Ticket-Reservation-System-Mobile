package com.example.ead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;




import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {



    private static final String BASE_URL = "https://studentmanagement20231009140446.azurewebsites.net/";
    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentAdapter(this);
        recyclerView.setAdapter(adapter);

        // Create a Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of the API interface
        StudentApi studentApi = retrofit.create(StudentApi.class);

        // Make the API request
        Call<List<Student>> call = studentApi.getStudents();
        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.isSuccessful()) {
                    // The API call was successful
                    List<Student> students = response.body();
                    // Update the RecyclerView with the list of students
                    adapter.setStudentList(students);
                } else {
                    // Handle the error response
                    Log.e(TAG, "API call failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                // Handle failure
                Log.e(TAG, "API call failed: " + t.getMessage());
            }
        });


    }
}
