package com.group05.emarket.utilities;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatter {
    private static final NumberFormat _currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("en", "US"));

    public static String format(double value) {
        return _currencyFormatter.format(value);
    }
}
