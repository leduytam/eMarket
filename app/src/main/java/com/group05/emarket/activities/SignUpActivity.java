package com.group05.emarket.activities;

import static com.group05.emarket.utilities.Validator.isValidEmail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.group05.emarket.R;
import com.group05.emarket.firestore.ContactsFirestoreManager;
import com.group05.emarket.models.Contact;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {
    LinearLayout indicators, passwordValidationContainer, step1Container, step2Container, step3Container;
    CardView indicator1, indicator2, indicator3, step1Indicator, step2Indicator, step3Indicator;
    TextView step1, step2, step3;
    TextInputEditText email, password;
    TextInputLayout emailLayout, passwordLayout;
    Button signUpButton;

    private ContactsFirestoreManager contactsFirestoreManager;

    int ERROR_DARK = R.color.ERROR_DARK;
    int GRAY_400 = R.color.GRAY_400;

    ArrayList<String> passwordValidation = new ArrayList<>() {
        {
            add("Must contain at least 8 characters");
            add("One number");
            add("One special character");
        }
    };

    boolean validatePassword(String value) {
        boolean flag = true;
        if (value.length() < 8) {
            indicator1.setCardBackgroundColor(getResources().getColor(GRAY_400));
            step1Indicator.setCardBackgroundColor(getResources().getColor(GRAY_400));
            flag = flag && false;
        } else {
            indicator1.setCardBackgroundColor(getResources().getColor(R.color.PRIMARY));
            step1Indicator.setCardBackgroundColor(getResources().getColor(R.color.PRIMARY));
            flag = flag && true;
        }

        if (value.matches(".*\\d.*")) {
            indicator2.setCardBackgroundColor(getResources().getColor(R.color.PRIMARY));
            step2Indicator.setCardBackgroundColor(getResources().getColor(R.color.PRIMARY));
            flag = flag && true;
        } else {
            indicator2.setCardBackgroundColor(getResources().getColor(GRAY_400));
            step2Indicator.setCardBackgroundColor(getResources().getColor(GRAY_400));
            flag = flag && false;
        }

        if (value.matches(".*\\W.*")) {
            indicator3.setCardBackgroundColor(getResources().getColor(R.color.PRIMARY));
            step3Indicator.setCardBackgroundColor(getResources().getColor(R.color.PRIMARY));
            flag = flag && true;
        } else {
            indicator3.setCardBackgroundColor(getResources().getColor(GRAY_400));
            step3Indicator.setCardBackgroundColor(getResources().getColor(GRAY_400));
            flag = flag && false;
        }

        return flag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contactsFirestoreManager = ContactsFirestoreManager.newInstance();
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.email_edit_text);
        password = findViewById(R.id.password_edit_text);
        emailLayout = findViewById(R.id.email_text_input_layout);
        passwordLayout = findViewById(R.id.password_text_input_layout);

        indicators = findViewById(R.id.indicatorContainer);
        passwordValidationContainer = findViewById(R.id.passwordValidationContainer);
        step1Container = findViewById(R.id.step1Container);
        step2Container = findViewById(R.id.step2Container);
        step3Container = findViewById(R.id.step3Container);
        indicator1 = findViewById(R.id.indicator1);
        indicator2 = findViewById(R.id.indicator2);
        indicator3 = findViewById(R.id.indicator3);
        step1Indicator = findViewById(R.id.step1Indicator);
        step2Indicator = findViewById(R.id.step2Indicator);
        step3Indicator = findViewById(R.id.step3Indicator);

        for (int i = 0; i < passwordValidation.size(); i++) {
            switch (i) {
                case 0:
                    step1 = new TextView(this);
                    step1.setText(passwordValidation.get(i));
                    step1Container.addView(step1);
                    break;
                case 1:
                    step2 = new TextView(this);
                    step2.setText(passwordValidation.get(i));
                    step2Container.addView(step2);
                    break;
                case 2:
                    step3 = new TextView(this);
                    step3.setText(passwordValidation.get(i));
                    step3Container.addView(step3);
                    break;
            }
        }

        // event
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    var res = isValidEmail(s.toString());
                    if (res) {
                        emailLayout.setErrorEnabled(false);
                        emailLayout.setBoxStrokeColor(getResources().getColor(R.color.PRIMARY));
                    } else {
                        emailLayout.setErrorEnabled(true);
                        emailLayout.setError("Invalid email");
                        emailLayout.setErrorTextColor(ColorStateList.valueOf(ContextCompat.getColor(SignUpActivity.this, R.color.ERROR)));
                        emailLayout.setBoxStrokeColor(getResources().getColor(R.color.ERROR));
                    }
                } else {
                    emailLayout.setBoxStrokeColor(getResources().getColor(R.color.GRAY_400));
                    emailLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    var res = validatePassword(s.toString());
                    if (res) {
                        passwordLayout.setBoxStrokeColor(getResources().getColor(R.color.PRIMARY));
                    } else {
                        passwordLayout.setErrorTextColor(ColorStateList.valueOf(ContextCompat.getColor(SignUpActivity.this, R.color.ERROR)));
                        passwordLayout.setBoxStrokeColor(getResources().getColor(R.color.ERROR));
                    }
                } else {
                    passwordLayout.setBoxStrokeColor(getResources().getColor(R.color.PRIMARY));
                    indicator1.setCardBackgroundColor(getResources().getColor(R.color.GRAY_400));
                    indicator2.setCardBackgroundColor(getResources().getColor(R.color.GRAY_400));
                    indicator3.setCardBackgroundColor(getResources().getColor(R.color.GRAY_400));
                    step1.setTextColor(getResources().getColor(R.color.BLACK));
                    step2.setTextColor(getResources().getColor(R.color.BLACK));
                    step3.setTextColor(getResources().getColor(R.color.BLACK));
                    step1Indicator.setCardBackgroundColor(getResources().getColor(R.color.GRAY_400));
                    step2Indicator.setCardBackgroundColor(getResources().getColor(R.color.GRAY_400));
                    step3Indicator.setCardBackgroundColor(getResources().getColor(R.color.GRAY_400));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });

        signUpButton = findViewById(R.id.signup_button);
        signUpButton.setOnClickListener(v -> {
            var emailValue = email.getText().toString();
            var passwordValue = password.getText().toString();
            onSubmit(emailValue, passwordValue);
        });
    }

    private void onSubmit(String email, String password) {
        Contact contact = new Contact(email, password);
        var res = contactsFirestoreManager.createDocument(contact);
        if (res) {
            Toast.makeText(SignUpActivity.this, "Create successfully", Toast.LENGTH_LONG).show();
        }
    }
}