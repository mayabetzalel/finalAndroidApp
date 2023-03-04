package com.example.class3demo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText emailET, passwordET;
//    FirebaseAuth mAuth = FirebaseAuth.getInstance();

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

//        Button loginButton = findViewById(R.id.login_btn_login);
//
//        loginButton.setOnClickListener(view -> {
//
//            String email = emailET.getText().toString();
//            String password = passwordET.getText().toString();
//
//            if (TextUtils.isEmpty(email)) {
//                Toast.makeText(LoginActivity.this, "Enter email",
//                        Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            if (TextUtils.isEmpty(password)) {
//                Toast.makeText(LoginActivity.this, "Enter password",
//                        Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            mAuth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                // Go to main app
//                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                startActivity(intent);
//                                finish();
//                            } else {
//                                Toast.makeText(LoginActivity.this, "Authentication failed.",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//
//        });
    }
}