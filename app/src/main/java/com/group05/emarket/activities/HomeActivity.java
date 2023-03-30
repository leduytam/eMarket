package com.group05.emarket.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.group05.emarket.R;
import com.group05.emarket.adapters.CategoryAdapter;
import com.group05.emarket.adapters.ProductAdapter;
import com.group05.emarket.models.Category;
import com.group05.emarket.models.Product;

import java.util.ArrayList;
import java.util.Collections;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView _rvCategories;
    private RecyclerView _rvPopularProducts;
    private RecyclerView _rvNewestProducts;
    private RecyclerView _rvDiscountProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        _rvCategories = findViewById(R.id.rv_categories);
        _rvCategories.setAdapter(new CategoryAdapter(this, _getAllCategories()));
        _rvCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        _rvPopularProducts = findViewById(R.id.rv_popular_products);
        _rvPopularProducts.setAdapter(new ProductAdapter(this, _getProducts()));
        _rvPopularProducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        _rvNewestProducts = findViewById(R.id.rv_newest_products);
        _rvNewestProducts.setAdapter(new ProductAdapter(this, _getProducts()));
        _rvNewestProducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        _rvDiscountProducts = findViewById(R.id.rv_discount_products);
        _rvDiscountProducts.setAdapter(new ProductAdapter(this, _getProducts()));
        _rvDiscountProducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private static ArrayList<Category> _getAllCategories() {
        var categories = new ArrayList<Category>();

        categories.add(new Category("Vegetables", R.drawable.ic_button_vegetable));
        categories.add(new Category("Fruits", R.drawable.ic_button_fruit));
        categories.add(new Category("Meats", R.drawable.ic_button_meat));
        categories.add(new Category("Eggs", R.drawable.ic_button_egg));
        categories.add(new Category("Fishes", R.drawable.ic_button_vegetable));
        categories.add(new Category("Snacks", R.drawable.ic_button_fruit));
        categories.add(new Category("Beverages", R.drawable.ic_button_meat));
        categories.add(new Category("Bakeries", R.drawable.ic_button_egg));

        return categories;
    }

    private static ArrayList<Product> _getProducts() {
        var products = new ArrayList<Product>();

        var product1 = new Product.Builder()
                .setName("Bare Baked Crunchy Banana Chips 2.7 oz")
                .setImage(R.drawable.image_product_1)
                .setPrice(2)
                .setRating(4.8f)
                .setRatingCount(81)
                .build();

        var product2 = new Product.Builder()
                .setName("Keripik Pisang")
                .setImage(R.drawable.image_product_2)
                .setPrice(1.2f)
                .setRating(4.2f)
                .setRatingCount(42)
                .build();

        var product3 = new Product.Builder()
                .setName("Beras Topi Koki Harum 5 Kilo")
                .setImage(R.drawable.image_product_3)
                .setPrice(3.5f)
                .setRating(3.2f)
                .setRatingCount(12)
                .build();

        var product4 = new Product.Builder()
                .setName("Beras 5 Kg")
                .setImage(R.drawable.image_product_4)
                .setPrice(2.5f)
                .setRating(4.6f)
                .setRatingCount(104)
                .build();

        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);

        Collections.shuffle(products);

        return products;
    }
}