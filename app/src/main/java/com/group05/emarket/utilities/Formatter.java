package com.group05.emarket.utilities;

import androidx.annotation.NonNull;

import java.text.NumberFormat;
import java.util.Locale;

public class Formatter {
    private static final Locale locale = new Locale("en", "US");
    private static final NumberFormat _currencyFormatter = NumberFormat.getCurrencyInstance(locale);

    @NonNull
    public static String formatCurrency(double value) {
        return _currencyFormatter.format(value);
    }
}
