package com.example.ead;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeleteStudentActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://studentmanagement20231009140446.azurewebsites.net/";
    private String studentId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_student);


        Intent intent = getIntent();
        String studentIdStr = intent.getStringExtra("studentId");




        studentId = getIntent().getStringExtra("studentId");

        Button deleteButton = findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener(view -> {

            deleteStudent();
        });
    }

    private void deleteStudent() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        UserApi studentApi = retrofit.create(UserApi.class);


        Call<Void> call = studentApi.deleteStudent(String.valueOf(studentId));

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    showSuccessToast();
                    setResult(RESULT_OK);
                    finish();
                } else {

                    showErrorToast();
                }
            }

            @Override

            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure
                showErrorToast();
                if (t != null) {
                    Log.e("DeleteStudentActivity", "Error: " + t.getMessage());
                }
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
