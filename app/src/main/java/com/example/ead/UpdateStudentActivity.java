package com.example.ead;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ead.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.widget.Toast;

import java.io.IOException;

public class UpdateStudentActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextGender;
    private Button buttonUpdate;
    private String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextGender = findViewById(R.id.editTextGender);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        studentId = getIntent().getStringExtra("studentId");
        String studentGender = getIntent().getStringExtra("gender");
        String studentName = getIntent().getStringExtra("name");
        int studentAge = getIntent().getIntExtra("age", 0);

        editTextName.setText(studentName);
        editTextGender.setText(studentGender);
        editTextAge.setText(String.valueOf(studentAge));

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String updatedName = editTextName.getText().toString().trim();
                String updatedGender = editTextGender.getText().toString().trim();
                int updatedAge = Integer.parseInt(editTextAge.getText().toString().trim());

                Student updatedStudent = new Student(updatedName, updatedAge, updatedGender);
                updatedStudent.setId(studentId);

                String apiBaseUrl = "https://studentmanagement20231009140446.azurewebsites.net/";

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(apiBaseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                StudentApi studentApi = retrofit.create(StudentApi.class);

                Call<Void> call = studentApi.updateStudent(studentId, updatedStudent);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            showToast("Student data updated successfully");
                            finish();
                        } else {
                            showToast("Error: Failed to update student data");
                            Log.e("UpdateStudent", "Failed to update student data: " + response.errorBody());
                        }
                    }


                    @Override

                    public void onFailure(Call<Void> call, Throwable t) {
                        if (t instanceof IOException) {
                            // Network or conversion error (e.g., parsing JSON)
                            Log.e("UpdateStudent", "Network or conversion error: " + t.getMessage());
                        } else {
                            // Unexpected error
                            Log.e("UpdateStudent", "Unexpected error: " + t.getMessage());
                        }
                    }


                });
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}


