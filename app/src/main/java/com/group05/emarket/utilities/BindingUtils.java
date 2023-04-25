package com.group05.emarket.utilities;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class BindingUtils {
    public static float getDiscountedPrice(float price, int discount) {
        return price * (1.0f - discount / 100f);
    }
    @BindingAdapter("app:price")
    public static void formatPrice(TextView view, float price) {
        view.setText(Formatter.formatCurrency(price));
    }

    @BindingAdapter("app:image")
    public static void setImage(ImageView view, String image) {
        Glide.with(view.getContext()).load(image).into(view);
    }
}
