package com.example.class3demo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.class3demo2.model.Model;
import com.example.class3demo2.model.User;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        User loggedInUser = Model.instance().getLoggedInUser();
        Intent intent;

        if (loggedInUser != null) {
            // The user is logged in so we go to MainActivity
            intent = new Intent(this, MainActivity.class);
        } else {
            // The user is logged in so we go to LoginActivity
            intent = new Intent(this, LoginActivity.class);
        }

        startActivity(intent);
        finish();
    }
}