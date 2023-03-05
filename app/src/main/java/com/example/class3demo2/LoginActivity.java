package com.example.class3demo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.class3demo2.model.FirebaseModel;
import com.example.class3demo2.model.Model;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity implements FirebaseModel.OnLoginCompleteListener  {

    EditText emailET, passwordET;
//    FirebaseAuth mAuth = FirebaseAuth.getInstance();

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
        Log.d("lotan", "onLoginComplete");
        if (success) {
            Log.d("lotan", "success");
            // Login successful, continue with app flow
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Log.d("lotan", "not success");
            // Login unsuccessful, show error message
            Snackbar.make(findViewById(android.R.id.content), "Email or password are incorrect", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

//                    // Todo: save user data
//
//
//                    Intent intent = new Intent(this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//
//                    // Todo: know if there is error
////                    Log.d("lotan", "Error in login: " + exception.getMessage());
////                    Snackbar.make(findViewById(android.R.id.content), "Email or password are incorect", Snackbar.LENGTH_LONG).show();


                };
            });



        }




    }
