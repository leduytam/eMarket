package com.group05.emarket.utilities;

import android.text.TextUtils;

public class Validator {
    public static boolean isValidEmail(CharSequence email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    public static boolean isValidPassword(String password) {
        // not empty + at least 8 characters + at least 1 number + at least 1 special character
        return !TextUtils.isEmpty(password) && password.length() >= 8 && password.matches(".*\\d.*") && password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
    }
}
