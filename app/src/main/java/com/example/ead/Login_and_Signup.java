package com.example.ead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login_and_Signup extends AppCompatActivity {

    public void onLoginButtonClick(View view) {
        Intent loginIntent = new Intent(this, New_Login.class);
        startActivity(loginIntent);
    }

    public void onSignUpButtonClick(View view) {
        Intent loginIntent = new Intent(this, NewSignUp.class);
        startActivity(loginIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_signup);

    }

}