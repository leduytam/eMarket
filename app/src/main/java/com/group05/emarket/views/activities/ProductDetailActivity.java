package com.group05.emarket.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.group05.emarket.MockData;
import com.group05.emarket.R;
import com.group05.emarket.databinding.ActivityProductDetailBinding;
import com.group05.emarket.enums.EProductListType;
import com.group05.emarket.models.Product;
import com.group05.emarket.viewmodels.CartViewModel;
import com.group05.emarket.viewmodels.ProductDetailViewModel;
import com.group05.emarket.viewmodels.ProductListViewModel;
import com.group05.emarket.views.adapters.ProductAdapter;
import com.group05.emarket.utilities.Formatter;
import com.group05.emarket.views.fragments.OverviewProductsFragment;

import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

public class ProductDetailActivity extends AppCompatActivity {
    private CartViewModel cartViewModel;
    private ProductDetailViewModel productDetailViewModel;
    private OverviewProductsFragment overviewProductsFragment;

    @SuppressLint("UnsafeOptInUsageError")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.group05.emarket.databinding.ActivityProductDetailBinding binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        var product = (Product) getIntent().getParcelableExtra("product");

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        overviewProductsFragment = OverviewProductsFragment.newInstance(EProductListType.RELATED, product);
        transaction.replace(R.id.fragment_overview_related_products, overviewProductsFragment);
        transaction.commit();

        productDetailViewModel = new ViewModelProvider(this, new ProductDetailViewModel.Factory(product)).get(ProductDetailViewModel.class);

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

        binding.tvName.setText(product.getName());

        binding.tvWeight.setText(String.format(Locale.US, "%s%s", product.getWeight(), product.getWeightUnit()));

        binding.tvDiscount.setText(String.format(Locale.US, "%d%%", product.getDiscount()));

        Glide.with(this)
                .load(product.getImage())
                .into(binding.ivImage);

        binding.tvOldPrice.setPaintFlags(binding.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        binding.tvDescription.setText(product.getDescription());
        binding.tvRatingCount.setText(String.format("Reviews (%s)", product.getRatingCount()));
        binding.rbRatingStars.setRating((product.getAvgRating()));
        binding.btnOpenReviews.setOnClickListener(v -> {
            binding.btnOpenReviews.setChecked(false);
            Intent intent = new Intent(this, ReviewActivity.class);
            intent.putExtra("productId", product.getId());
            startActivity(intent);
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
            cartViewModel.addItemToCart(product, productDetailViewModel.getQuantity().getValue());
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
        });

        binding.btnAddQuantity.setOnClickListener(v -> {
            int quantity = productDetailViewModel.getQuantity().getValue();
            productDetailViewModel.setQuantity(quantity + 1);
        });

        binding.btnRemoveQuantity.setOnClickListener(v -> {
            int quantity = productDetailViewModel.getQuantity().getValue();

            if (quantity > 1) {
                productDetailViewModel.setQuantity(quantity - 1);
            }
        });

        productDetailViewModel.getQuantity().observe(this, quantity -> {
            binding.tvQuantity.setText(String.valueOf(quantity));
        });

        productDetailViewModel.getOverviewRelatedProducts().observe(this, products -> {
            overviewProductsFragment.setProducts(products);
        });

        productDetailViewModel.getIsLoading().observe(this, isLoading -> {
            overviewProductsFragment.setIsLoading(isLoading);
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