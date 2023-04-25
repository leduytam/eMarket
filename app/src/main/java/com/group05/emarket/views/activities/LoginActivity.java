package com.group05.emarket.views.activities;

import static com.group05.emarket.utilities.Validator.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.group05.emarket.R;
import com.group05.emarket.firestore.UsersFirestoreManager;
import com.group05.emarket.models.User;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText email, password;
    TextInputLayout emailLayout, passwordLayout;

    MaterialAlertDialogBuilder alertDialogBuilder;

    UsersFirestoreManager usersFirestoreManager;

    FirebaseAuth mAuth;

    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usersFirestoreManager = UsersFirestoreManager.newInstance();
        email = findViewById(R.id.email_edit_text);
        password = findViewById(R.id.password_edit_text);
        emailLayout = findViewById(R.id.email_text_input_layout);
        passwordLayout = findViewById(R.id.password_text_input_layout);
        alertDialogBuilder = new MaterialAlertDialogBuilder(this);
        mAuth = FirebaseAuth.getInstance();
        MaterialToolbar topBar = findViewById(R.id.top_bar);
        topBar.setNavigationOnClickListener(v -> finish());

        email.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (!isValidEmail(email.getText().toString())) {
                    emailLayout.setErrorEnabled(true);
                    emailLayout.setError("Invalid email");
                    emailLayout.setErrorTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.ERROR)));
                    emailLayout.setBoxStrokeColor(getResources().getColor(R.color.ERROR));
                }
            } else {
                emailLayout.setErrorEnabled(false);
                emailLayout.setBoxStrokeColor(getResources().getColor(R.color.PRIMARY));
            }
        });

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> {
            var emailValue = email.getText().toString();
            var passwordValue = password.getText().toString();
            try {
                usersFirestoreManager.login(new User(emailValue, passwordValue)).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        if (currentUser != null) {
                            if (currentUser.isEmailVerified()) {
                                Intent intent = new Intent(LoginActivity.this, LayoutActivity.class);
                                startActivity(intent);
                            } else {
                                alertDialogBuilder.setTitle("Login failed").setMessage("Please verify your email").setPositiveButton("OK", (dialog, which) -> {
                                    dialog.dismiss();
                                }).setBackground(getResources().getDrawable(R.drawable.background_dialog_alert)).show();
                            }
                        }
                    } else {
                        String error = task.getException().getMessage();
                        alertDialogBuilder.setTitle("Login failed").setMessage(error).setPositiveButton("OK", (dialog, which) -> {
                            dialog.dismiss();
                        }).setBackground(getResources().getDrawable(R.drawable.background_dialog_alert)).show();
                    }
                });
            } catch (Exception e) {
                alertDialogBuilder.setTitle("Login failed").setMessage(e.getMessage()).setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                }).setBackground(getResources().getDrawable(R.drawable.background_dialog_alert)).show();
            }
        });
    }
}