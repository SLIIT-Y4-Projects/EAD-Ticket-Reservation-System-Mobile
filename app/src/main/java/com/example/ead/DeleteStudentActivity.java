package com.example.ead;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeleteStudentActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://studentmanagement20231009140446.azurewebsites.net/";
    private int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_student);

        // Get the student ID passed from the previous activity as a String
        Intent intent = getIntent();
        String studentIdStr = intent.getStringExtra("studentId");

        // Attempt to parse the String studentIdStr to an int
        try {
            studentId = Integer.parseInt(studentIdStr);
        } catch (NumberFormatException e) {
            // Handle the case where the provided student ID is not a valid integer
            studentId = -1; // Provide a default value or handle the error as needed
        }

        // Initialize UI elements and implement the delete operation
        Button deleteButton = findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener(view -> {
            // Call the API to delete the student
            deleteStudent();
        });
    }

    private void deleteStudent() {
        // Create a Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of the API interface
        StudentApi studentApi = retrofit.create(StudentApi.class);

        // Make the DELETE API request
        Call<Void> call = studentApi.deleteStudent(String.valueOf(studentId));
        // Assuming your API method is named "deleteStudent"
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Deletion was successful
                    showSuccessToast();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    // Handle the error response
                    showErrorToast();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure
                showErrorToast();
            }
        });
    }

    private void showSuccessToast() {
        Toast.makeText(this, "Student deleted successfully", Toast.LENGTH_SHORT).show();
    }

    private void showErrorToast() {
        Toast.makeText(this, "Failed to delete student", Toast.LENGTH_SHORT).show();
    }
}
