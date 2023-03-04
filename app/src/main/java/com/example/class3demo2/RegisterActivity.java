package com.example.class3demo2;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.class3demo2.model.Model;
import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity {
    Boolean isAvatarSelected = false;
    ImageView avatarImg;

    private Boolean validateRegister(View view) {

        EditText nameET = findViewById(R.id.register_name_et);
        EditText emailET = findViewById(R.id.register_email_et);
        EditText passwordET = findViewById(R.id.register_password_et);

        String name = nameET.getText().toString();
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Snackbar.make(findViewById(android.R.id.content), "Please enter your name", Snackbar.LENGTH_LONG).show();
            return false;
        } else if (name.length() < 4) {
        Snackbar.make(findViewById(android.R.id.content), "Full name is too short", Snackbar.LENGTH_LONG).show();
        return false;
    }

        if (TextUtils.isEmpty(email)) {
            Snackbar.make(findViewById(android.R.id.content), "Please enter your email", Snackbar.LENGTH_LONG).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Snackbar.make(findViewById(android.R.id.content), "Please enter a valid email address", Snackbar.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Snackbar.make(findViewById(android.R.id.content), "Please enter your password", Snackbar.LENGTH_LONG).show();
            return false;
        } else if (password.length() < 6) {
            Snackbar.make(findViewById(android.R.id.content), "Password must be at least 6 characters long", Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView goToLogin = findViewById(R.id.register_tv_login_page);

        goToLogin.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        ImageButton galleryButton = findViewById(R.id.galleryButton);
        ImageButton cameraButton = findViewById(R.id.cameraButton);
        avatarImg = findViewById(R.id.avatarImg);

        ActivityResultLauncher<Void> cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    avatarImg.setImageBitmap(result);
                    isAvatarSelected = true;
                }
            }
        });

        ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    avatarImg.setImageURI(result);
                    isAvatarSelected = true;
                }
            }
        });

        cameraButton.setOnClickListener(view->{
            cameraLauncher.launch(null);
        });

        galleryButton.setOnClickListener(view->{
            galleryLauncher.launch("image/*");
        });

        Button registerBtn = findViewById(R.id.register_btn_register);
        registerBtn.setOnClickListener(view -> {

            // validation
            if (validateRegister(view)) {
                EditText nameET = findViewById(R.id.register_name_et);
                EditText emailET = findViewById(R.id.register_email_et);
                EditText passwordET = findViewById(R.id.register_password_et);

                String name = nameET.getText().toString();
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();

                if (!isAvatarSelected) {
                    avatarImg = null;
                }

                try {
                    Model.instance().registerUser(name, email, password, avatarImg);
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception exception) {
                    Log.d("lotan", exception.getMessage());
                    Snackbar.make(findViewById(android.R.id.content), "Error while registration, Please try again later", Snackbar.LENGTH_LONG).show();
                }
            }


        });



    }
}