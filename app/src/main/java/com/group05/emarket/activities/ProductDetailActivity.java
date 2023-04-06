package com.group05.emarket.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Paint;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.group05.emarket.MockData;
import com.group05.emarket.R;
import com.group05.emarket.adapters.ProductAdapter;
import com.group05.emarket.adapters.ReviewAdapter;
import com.group05.emarket.utilities.Formatter;

import java.util.Locale;
import java.util.UUID;

public class ProductDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        UUID productId = (UUID) getIntent().getSerializableExtra("id");
        var product = MockData.getProductById(productId);

        MaterialToolbar topBar = findViewById(R.id.top_bar);
        topBar.setNavigationOnClickListener(v -> finish());

        topBar.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.action_cart) {
                Intent intent = new Intent(this, CartActivity.class);
                startActivity(intent);
            } else if (itemId == R.id.action_share) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "eMarket");
                startActivity(Intent.createChooser(intent, "Share"));
            } else {
                return false;
            }

            return true;
        });

        RecyclerView rvReviews = findViewById(R.id.rv_reviews);
        rvReviews.setAdapter(new ReviewAdapter(this, MockData.getReviews()));
        rvReviews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        RecyclerView rvRelatedProducts = findViewById(R.id.rv_related_products);
        rvRelatedProducts.setAdapter(new ProductAdapter(this, MockData.getProducts().subList(0, 3)));
        rvRelatedProducts.setLayoutManager(new GridLayoutManager(this, 3));

        TextView tvName = findViewById(R.id.tv_name);
        TextView tvDiscount = findViewById(R.id.tv_discount);
        ImageView ivImage = findViewById(R.id.iv_image);
        TextView tvPrice = findViewById(R.id.tv_price);
        TextView tvOldPrice = findViewById(R.id.tv_old_price);
        tvOldPrice.setPaintFlags(tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        TextView tvAvgRating = findViewById(R.id.tv_avg_rating);
        TextView tvRatingCount = findViewById(R.id.tv_rating_count);
        TextView tvDescription = findViewById(R.id.tv_description);
        TextView tvAvgRatingDetail = findViewById(R.id.tv_avg_rating_detail);
        TextView tvRatingCountDetail = findViewById(R.id.tv_rating_count_detail);

        tvName.setText(product.getName());
        tvDiscount.setText(String.format(Locale.US, "%d%%", product.getDiscount()));
        ivImage.setImageResource(product.getImage());
        tvAvgRating.setText(String.valueOf(product.getAvgRating()));
        tvRatingCount.setText(String.format(Locale.US, "%d Reviews", product.getRatingCount()));
        tvDescription.setText(product.getDescription());
        tvRatingCountDetail.setText(String.format("Reviews (%s)", product.getRatingCount()));
        tvAvgRatingDetail.setText(String.valueOf(product.getAvgRating()));

        if (product.getDiscount() == 0) {
            RelativeLayout rlDiscount = findViewById(R.id.rl_discount);
            rlDiscount.setVisibility(View.GONE);
            tvOldPrice.setVisibility(View.GONE);
            tvPrice.setText(Formatter.formatCurrency(product.getPrice()));
        } else {
            float discountPrice = product.getPrice() * (1 - product.getDiscount() / 100f);
            tvPrice.setText(Formatter.formatCurrency(discountPrice));
            tvOldPrice.setText(Formatter.formatCurrency(product.getPrice()));
        }
    }
}