package com.example.ead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GetStart extends AppCompatActivity {
    public void onGetStartButtonClick(View view) {
        Intent loginIntent = new Intent(this, Login_and_Signup.class);
        startActivity(loginIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_start);



    }

}