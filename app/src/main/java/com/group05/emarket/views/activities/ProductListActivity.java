package com.group05.emarket.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.appbar.MaterialToolbar;
import com.group05.emarket.MockData;
import com.group05.emarket.R;
import com.group05.emarket.views.adapters.ProductAdapter;
import com.group05.emarket.views.adapters.SearchResultsAdapter;
import com.group05.emarket.views.decorations.GridGapItemDecoration;
import com.group05.emarket.views.dialogs.FilterSortingDialog;

public class ProductListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        MaterialToolbar topBar = findViewById(R.id.top_bar);
        topBar.setNavigationOnClickListener(v -> finish());
        topBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_filter_sort:
                    FilterSortingDialog dialog = new FilterSortingDialog();
                    dialog.show(getSupportFragmentManager(), "FilterSortingDialog");
                    return true;
            }
            return false;
        });

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            topBar.setTitle(extras.getString("title"));
        }

        SearchView searchBar = findViewById(R.id.search_bar);
        searchBar.setIconifiedByDefault(false);

        RecyclerView rvProducts = findViewById(R.id.rv_products);
        rvProducts.setAdapter(new ProductAdapter(this, MockData.getProducts()));
        rvProducts.setLayoutManager(new GridLayoutManager(this, 3));
        rvProducts.addItemDecoration(new GridGapItemDecoration(3, 10, false));
    }
}