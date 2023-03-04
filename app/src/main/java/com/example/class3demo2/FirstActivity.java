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
        if (isLoggedIn) {
            Log.d("lotan", "going to main activity");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Log.d("lotan", "going to auth activity");
            Intent intent = new Intent(this, AuthActivty.class);
            startActivity(intent);
        }
    }
}