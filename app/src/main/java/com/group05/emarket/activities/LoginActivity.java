package com.group05.emarket.activities;

import static com.group05.emarket.utilities.Validator.isValidEmail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.group05.emarket.R;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText email, password;
    TextInputLayout emailLayout, passwordLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email_edit_text);
        password = findViewById(R.id.password_edit_text);
        emailLayout = findViewById(R.id.email_text_input_layout);
        passwordLayout = findViewById(R.id.password_text_input_layout);



        email.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (!isValidEmail(email.getText().toString())) {
                    emailLayout.setErrorEnabled(true);
                    emailLayout.setError("Invalid email");
                    emailLayout.setErrorTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.ERROR)));
                    emailLayout.setBoxStrokeColor(getResources().getColor(R.color.ERROR));
                }
            }
            else {
                emailLayout.setErrorEnabled(false);
                emailLayout.setBoxStrokeColor(getResources().getColor(R.color.PRIMARY));
            }
        });

    }

    public static class AuthenticationActivity extends AppCompatActivity {

        private Button mSignupButton;
        private Button mLoginButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_authentication);

            mSignupButton = findViewById(R.id.signup_button);
            mLoginButton = findViewById(R.id.login_button);

            mSignupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AuthenticationActivity.this, SignUpActivity.class);
                    startActivity(intent);
                }
            });

            mLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AuthenticationActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}