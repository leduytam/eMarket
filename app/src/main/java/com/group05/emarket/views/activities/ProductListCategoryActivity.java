package com.group05.emarket.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.group05.emarket.MockData;
import com.group05.emarket.R;
import com.group05.emarket.models.Category;
import com.group05.emarket.views.adapters.ProductAdapter;
import com.group05.emarket.views.decorations.GridGapItemDecoration;
import com.group05.emarket.views.dialogs.FilterSortingDialog;

import java.util.UUID;

public class ProductListCategoryActivity extends AppCompatActivity {
    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_product_list);

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
            UUID categoryId = (UUID) extras.getSerializable("categoryId");
            category = MockData.getCategoryById(categoryId);
            topBar.setTitle(category.getName());
        }

        RecyclerView rvProducts = findViewById(R.id.rv_products);
        var adapter = new ProductAdapter(this, MockData.getProductsByCategory(category.getId()));
        rvProducts.setAdapter(adapter);
        rvProducts.setLayoutManager(new GridLayoutManager(this, 3));
        rvProducts.addItemDecoration(new GridGapItemDecoration(3, 10, false));

        SearchView searchBar = findViewById(R.id.search_bar);
        searchBar.setIconifiedByDefault(false);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.setProducts(MockData.getProducts(newText, category.getId()));
                return false;
            }
        });
    }
}