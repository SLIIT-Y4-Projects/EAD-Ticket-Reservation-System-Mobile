package com.example.ead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookingActivity extends AppCompatActivity {
    private String trainId;
    private static final String BASE_URL = "https://ticketreservationsystemapinew.azurewebsites.net/";
    private EditText etReservationDate;
    private UserApi reservationApi;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("user_id", null);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        etReservationDate = findViewById(R.id.etReservationDate);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        reservationApi = retrofit.create(UserApi.class);

        Button btnSubmit = findViewById(R.id.btnTrainView);

        // Retrieve the train ID from the intent
        Intent intent = getIntent();
        trainId = intent.getStringExtra("trainId");

        // Find the trainIdValueTextView by its ID
        TextView trainIdValueTextView = findViewById(R.id.trainIdValueTextView);

        // Set the train ID in the TextView
        trainIdValueTextView.setText(trainId);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = etReservationDate.getText().toString();

                Reservation reservation = new Reservation();
                reservation.setReservationDate(date);
                reservation.setPassengerId(userID);
                reservation.setTrainId(trainId);
                reservation.setStatus("ACTIVE");

                Call<ReservationResponse> call = reservationApi.addReservation(reservation);

                call.enqueue(new Callback<ReservationResponse>() {

                    @Override
                    public void onResponse(Call<ReservationResponse> call, Response<ReservationResponse> response) {
                        if (response.isSuccessful()) {
                            // Handle a successful response (e.g., show a success message)
                            ReservationResponse reservationResponse = response.body();
                            String message = reservationResponse.getMessage();
                            showToast("Success: " + message);

                            // Display a success message or navigate back, etc.

                            Intent intent = new Intent(BookingActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Handle an error response (e.g., show an error message)
                            // You can access the error response using response.errorBody()
                            showToast("Error: Failed to add reservation.");
                        }
                    }

                    @Override
                    public void onFailure(Call<ReservationResponse> call, Throwable t) {
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
