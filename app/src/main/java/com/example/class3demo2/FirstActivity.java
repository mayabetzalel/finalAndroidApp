package com.example.class3demo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Boolean isLoggedIn = false;
        Intent intent;
        if (isLoggedIn) {
            Log.d("lotan", "going to main activity");
            intent = new Intent(this, MainActivity.class);
        } else {
            Log.d("lotan", "going to login activity");
            intent = new Intent(this, LoginActivity.class);
        }

        startActivity(intent);
        finish();
    }
}