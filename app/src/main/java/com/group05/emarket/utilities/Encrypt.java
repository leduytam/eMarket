package com.group05.emarket.utilities;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] byteData = md.digest();
            return Base64.encodeToString(byteData, Base64.DEFAULT);
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
