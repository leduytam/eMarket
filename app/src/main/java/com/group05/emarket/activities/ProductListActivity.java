package com.group05.emarket.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.appbar.MaterialToolbar;
import com.group05.emarket.R;
import com.group05.emarket.adapters.ProductAdapter;
import com.group05.emarket.models.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class ProductListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        MaterialToolbar topBar = findViewById(R.id.top_bar);
        topBar.setNavigationOnClickListener(v -> finish());

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            topBar.setTitle(extras.getString("title"));
        }

        SearchView searchBar = findViewById(R.id.search_bar);
        searchBar.setIconifiedByDefault(false);

        Button btnFilterSorting = findViewById(R.id.btn_filter_sorting);
        btnFilterSorting.setOnClickListener(v -> {
        });

        RecyclerView rvProducts = findViewById(R.id.rv_products);
        rvProducts.setAdapter(new ProductAdapter(this, _getProducts()));
        rvProducts.setLayoutManager(new GridLayoutManager(this, 3));
        rvProducts.addItemDecoration(new ProductAdapter.ProductItemDecoration(3, 10, false));
    }

    private static ArrayList<Product> _getProducts() {
        var products = new ArrayList<Product>();

        var product1 = new Product.Builder()
                .setId(UUID.randomUUID())
                .setName("Bare Baked Crunchy Banana Chips 2.7 oz")
                .setImage(R.drawable.image_product_1)
                .setPrice(2)
                .setRating(4.8f)
                .setRatingCount(81)
                .setDiscount(30)
                .build();

        var product2 = new Product.Builder()
                .setId(UUID.randomUUID())
                .setName("Keripik Pisang")
                .setImage(R.drawable.image_product_2)
                .setPrice(1.2f)
                .setRating(4.2f)
                .setRatingCount(42)
                .setDiscount(12)
                .build();

        var product3 = new Product.Builder()
                .setId(UUID.randomUUID())
                .setName("Beras Topi Koki Harum 5 Kilo")
                .setImage(R.drawable.image_product_3)
                .setPrice(3.5f)
                .setRating(3.2f)
                .setRatingCount(12)
                .setDiscount(5)
                .build();

        var product4 = new Product.Builder()
                .setId(UUID.randomUUID())
                .setName("Beras 5 Kg")
                .setImage(R.drawable.image_product_4)
                .setPrice(2.5f)
                .setRating(4.6f)
                .setRatingCount(104)
                .setDiscount(0)
                .build();

        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);

        Collections.shuffle(products);

        return products;
    }
}