package com.group05.emarket.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;
import com.group05.emarket.MockData;
import com.group05.emarket.R;
import com.group05.emarket.databinding.ActivityProductDetailBinding;
import com.group05.emarket.viewmodels.CartViewModel;
import com.group05.emarket.views.adapters.ProductAdapter;
import com.group05.emarket.views.adapters.ReviewAdapter;
import com.group05.emarket.utilities.Formatter;

import java.util.Locale;
import java.util.UUID;

@ExperimentalBadgeUtils
public class ProductDetailActivity extends AppCompatActivity {
    private CartViewModel cartViewModel;

    private int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.group05.emarket.databinding.ActivityProductDetailBinding binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        quantity = 0;

        UUID productId = (UUID) getIntent().getSerializableExtra("id");
        var product = MockData.getProductById(productId);

        binding.topBar.setNavigationOnClickListener(v -> finish());
        binding.topBar.setOnMenuItemClickListener(item -> {
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

        binding.rvReviews.setAdapter(new ReviewAdapter(this, MockData.getReviews()));
        binding.rvReviews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvReviews.setVisibility(View.GONE);

        binding.rvRelatedProducts.setAdapter(new ProductAdapter(this, MockData.getProducts().subList(0, 3)));
        binding.rvRelatedProducts.setLayoutManager(new GridLayoutManager(this, 3));

        binding.tvName.setText(product.getName());

        binding.tvWeight.setText(String.format(Locale.US, "%s %s", product.getWeight(), product.getWeightUnit()));
        if (product.getCategory() != null) {
            binding.tvCategory.setText(product.getCategory().getName());
        }

        binding.tvDiscount.setText(String.format(Locale.US, "%d%%", product.getDiscount()));
        binding.ivImage.setImageResource(product.getImage());
        binding.tvOldPrice.setPaintFlags(binding.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        binding.tvDescription.setText(product.getDescription());
        binding.tvRatingCountDetail.setText(String.format("Reviews (%s)", product.getRatingCount()));
        binding.rbRatingStars.setRating((product.getAvgRating()));
        binding.btnExpandReviews.setOnClickListener(v -> {
            if (binding.rvReviews.getVisibility() == View.VISIBLE) {
                binding.rvReviews.setVisibility(View.GONE);
            } else {
                binding.rvReviews.setVisibility(View.VISIBLE);
            }
        });

        if (product.getDiscount() == 0) {
            RelativeLayout rlDiscount = findViewById(R.id.rl_discount);
            rlDiscount.setVisibility(View.GONE);
            binding.tvOldPrice.setVisibility(View.GONE);
            binding.tvPrice.setText(Formatter.formatCurrency(product.getPrice()));
        } else {
            float discountPrice = product.getPrice() * (1 - product.getDiscount() / 100f);
            binding.tvPrice.setText(Formatter.formatCurrency(discountPrice));
            binding.tvOldPrice.setText(Formatter.formatCurrency(product.getPrice()));
        }

        BadgeDrawable cartBadge = BadgeDrawable.create(this);
        BadgeUtils.attachBadgeDrawable(cartBadge, binding.topBar, binding.topBar.getMenu().findItem(R.id.action_cart).getItemId());

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.getCartItems().observe(this, cart -> {
            cartBadge.setNumber(cart.size());
            cartBadge.setVisible(cart.size() > 0);
        });

        binding.btnAddToCart.setOnClickListener(v -> {
            cartViewModel.addItemToCart(product, quantity);
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
        });

        binding.tvQuantity.setText(String.valueOf(quantity));
        binding.btnAddQuantity.setOnClickListener(v -> {
            quantity++;
            binding.tvQuantity.setText(String.valueOf(quantity));
        });

        binding.btnRemoveQuantity.setOnClickListener(v -> {
            if (quantity > 0) {
                quantity--;
                binding.tvQuantity.setText(String.valueOf(quantity));
            }
        });

        binding.tvDescription.setMaxLines(3);
        binding.tvDescription.setEllipsize(TextUtils.TruncateAt.END);
        binding.btnExpandDescription.setOnClickListener(v -> {
            if (binding.tvDescription.getMaxLines() == 3) {
                binding.tvDescription.setMaxLines(Integer.MAX_VALUE);
            } else {
                binding.tvDescription.setMaxLines(3);
            }
        });
    }
}