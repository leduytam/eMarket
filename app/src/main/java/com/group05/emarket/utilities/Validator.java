package com.group05.emarket.utilities;

import android.text.TextUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.util.Base64;

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

    public static String hashPassword(String password) {
        try {
            if (!isValidPassword(password)) {
                return null;
            }
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] byteData = md.digest();
            String base64Password = Base64.encodeToString(byteData, Base64.DEFAULT);
            return base64Password;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decodePassword(String password) {
        byte[] decodedString = Base64.decode(password, Base64.DEFAULT);
        return new String(decodedString);
    }
}
