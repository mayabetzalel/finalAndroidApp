package com.example.class3demo2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.class3demo2.model.FirebaseModel;
import com.example.class3demo2.model.Model;
import com.example.class3demo2.model.RandomDogPhotoModel;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

public class LoginActivity extends AppCompatActivity implements FirebaseModel.OnLoginCompleteListener  {

    EditText emailET, passwordET;

    private Boolean validateLogin(View view) {

        EditText emailET = findViewById(R.id.login_email_et);
        EditText passwordET = findViewById(R.id.login_password_et);

        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Snackbar.make(findViewById(android.R.id.content), "Please enter your email", Snackbar.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Snackbar.make(findViewById(android.R.id.content), "Please enter your password", Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    @Override
    public void onLoginComplete(boolean success) {
        if (success) {
            // Login successful so we go MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Login successful so we notify the user
            Snackbar.make(findViewById(android.R.id.content), "Email or password are incorrect", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Show the random image

        ImageView randomPhoto = findViewById(R.id.login_random_photo);

        LiveData<String> data = RandomDogPhotoModel.instance.getRandomDogPhoto();
        data.observe(this, photoURL->{
            Picasso.get().load(photoURL).into(randomPhoto);
        });


        TextView goToRegister = findViewById(R.id.register_tv_login_page);

        goToRegister.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        emailET = findViewById(R.id.login_email_et);
        passwordET = findViewById(R.id.login_password_et);

        Button loginButton = findViewById(R.id.login_btn_login);

        loginButton.setOnClickListener(view -> {

            String email = emailET.getText().toString();
            String password = passwordET.getText().toString();

            if (validateLogin(view)) {
                Model.instance().loginUser(email, password, this);
            }
        });
    }
}