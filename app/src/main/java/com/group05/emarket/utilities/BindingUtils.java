package com.group05.emarket.utilities;

public class BindingUtils {
    public static float getDiscountedPrice(float price, int discount) {
        return price * (1.0f - discount / 100f);
    }
}
