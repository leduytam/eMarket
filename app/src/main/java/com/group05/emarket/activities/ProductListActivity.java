package com.group05.emarket.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.appbar.MaterialToolbar;
import com.group05.emarket.MockData;
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
        rvProducts.setAdapter(new ProductAdapter(this, MockData.getProducts()));
        rvProducts.setLayoutManager(new GridLayoutManager(this, 3));
        rvProducts.addItemDecoration(new ProductAdapter.ProductItemDecoration(3, 10, false));
    }
}