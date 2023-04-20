package com.group05.emarket.views.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.group05.emarket.MockData;
import com.group05.emarket.R;
import com.group05.emarket.databinding.ActivityProductDetailBinding;
import com.group05.emarket.firestore.ProductsFirestoreManger;
import com.group05.emarket.models.Product;
import com.group05.emarket.schemas.ProductsFirestoreSchema;
import com.group05.emarket.viewmodels.CartViewModel;
import com.group05.emarket.views.adapters.ProductAdapter;
import com.group05.emarket.views.adapters.ReviewAdapter;
import com.group05.emarket.utilities.Formatter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

@ExperimentalBadgeUtils
public class ProductDetailActivity extends AppCompatActivity {
    private CartViewModel cartViewModel;
    List<Product> relatedProducts;

    private int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.group05.emarket.databinding.ActivityProductDetailBinding binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        quantity = 0;
        relatedProducts = new ArrayList<>();
        String productId = (String) getIntent().getSerializableExtra("id");
        ProductsFirestoreManger productsFirestoreManger = ProductsFirestoreManger.newInstance();
        productsFirestoreManger.getProductById(productId).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Product product = new Product.Builder()
                        .setDocumentId(task.getResult().getId())
                        .setName(task.getResult().getString(ProductsFirestoreSchema.NAME))
                        .setPrice(task.getResult().getDouble(ProductsFirestoreSchema.PRICE).floatValue())
                        .setDescription(task.getResult().getString(ProductsFirestoreSchema.DESCRIPTION))
                        .setWeight(task.getResult().getDouble(ProductsFirestoreSchema.WEIGHT).floatValue())
                        .setWeightUnit(task.getResult().getString(ProductsFirestoreSchema.WEIGHT_UNIT))
                        .setDiscount(task.getResult().getLong(ProductsFirestoreSchema.DISCOUNT).intValue())
                        .setImageUrl(task.getResult().getString(ProductsFirestoreSchema.IMAGE_URL))
                        .setCategoryUuid(task.getResult().getString(ProductsFirestoreSchema.CATEGORY_UUID))
                        .build();
                final String categoryUuid = product.getCategoryUuid();
                binding.tvName.setText(product.getName());

                binding.tvWeight.setText(String.format(Locale.US, "%s %s", product.getWeight(), product.getWeightUnit()));
                if (product.getCategory() != null) {
                    binding.tvCategory.setText(product.getCategory().getName());
                }

                binding.tvDiscount.setText(String.format(Locale.US, "%d%%", product.getDiscount()));
                Picasso.get().load(product.getImageUrl()).into(binding.ivImage);
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

                productsFirestoreManger.getProductsByCategory(categoryUuid).addOnCompleteListener(querySnapshotTask -> {
                    if (querySnapshotTask.isSuccessful()) {
                        for (QueryDocumentSnapshot document : querySnapshotTask.getResult()) {
                            if (document.getId().equals(productId)) {
                                continue;
                            }
                            Product relatedProduct = new Product.Builder()
                                    .setDocumentId(document.getId())
                                    .setName(document.getString(ProductsFirestoreSchema.NAME))
                                    .setPrice(document.getDouble(ProductsFirestoreSchema.PRICE).floatValue())
                                    .setDescription(document.getString(ProductsFirestoreSchema.DESCRIPTION))
                                    .setWeight(document.getDouble(ProductsFirestoreSchema.WEIGHT).floatValue())
                                    .setWeightUnit(document.getString(ProductsFirestoreSchema.WEIGHT_UNIT))
                                    .setDiscount(document.getLong(ProductsFirestoreSchema.DISCOUNT).intValue())
                                    .setImageUrl(document.getString(ProductsFirestoreSchema.IMAGE_URL))
                                    .setCategoryUuid(document.getString(ProductsFirestoreSchema.CATEGORY_UUID))
                                    .build();
                            relatedProducts.add(relatedProduct);
                        }
                        binding.rvRelatedProducts.setAdapter(new ProductAdapter(this, relatedProducts.subList(0, 3)));
                        binding.rvRelatedProducts.setLayoutManager(new GridLayoutManager(this, 3));
                    }
                });
            }
        });

//        productsFirestoreManger.getProductsByCategory(categoryUuid.get()).addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                for (QueryDocumentSnapshot document : task.getResult()) {
//                    Product product = new Product.Builder()
//                            .setDocumentId(document.getId())
//                            .setName(document.getString(ProductsFirestoreSchema.NAME))
//                            .setPrice(document.getDouble(ProductsFirestoreSchema.PRICE).floatValue())
//                            .setDescription(document.getString(ProductsFirestoreSchema.DESCRIPTION))
//                            .setWeight(document.getDouble(ProductsFirestoreSchema.WEIGHT).floatValue())
//                            .setWeightUnit(document.getString(ProductsFirestoreSchema.WEIGHT_UNIT))
//                            .setDiscount(document.getLong(ProductsFirestoreSchema.DISCOUNT).intValue())
//                            .setImageUrl(document.getString(ProductsFirestoreSchema.IMAGE_URL))
//                            .setCategoryUuid(document.getString(ProductsFirestoreSchema.CATEGORY_UUID))
//                            .build();
//                    relatedProducts.add(product);
//                }
//                binding.rvRelatedProducts.setAdapter(new ProductAdapter(this, relatedProducts.subList(0, 3)));
//                binding.rvRelatedProducts.setLayoutManager(new GridLayoutManager(this, 3));
//            }
//        });

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