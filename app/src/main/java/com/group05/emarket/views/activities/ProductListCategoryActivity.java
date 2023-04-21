package com.group05.emarket.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.group05.emarket.R;
import com.group05.emarket.databinding.ActivityCategoryProductListBinding;
import com.group05.emarket.models.Category;
import com.group05.emarket.viewmodels.ProductListCategoryViewModel;
import com.group05.emarket.views.adapters.ProductAdapter;
import com.group05.emarket.views.decorations.GridGapItemDecoration;
import com.group05.emarket.views.dialogs.FilterSortProductListDialog;

public class ProductListCategoryActivity extends AppCompatActivity {
    private ProductListCategoryViewModel viewModel;

    @SuppressLint("UnsafeOptInUsageError")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        var binding = ActivityCategoryProductListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        var extras = getIntent().getExtras();

        if (extras != null) {
            Category category = (Category) extras.getSerializable("category");
            binding.topBar.setTitle(category.getName());
            viewModel = new ViewModelProvider(this, new ProductListCategoryViewModel.Factory(category.getId())).get(ProductListCategoryViewModel.class);
        }

        BadgeDrawable filterSortBadge = BadgeDrawable.create(this);
        BadgeUtils.attachBadgeDrawable(filterSortBadge, binding.topBar, binding.topBar.getMenu().findItem(R.id.action_filter_sort).getItemId());
        filterSortBadge.setVisible(false);

        binding.topBar.setNavigationOnClickListener(v -> finish());
        binding.topBar.setOnMenuItemClickListener(item -> {
            var itemId = item.getItemId();

            if (itemId == R.id.action_filter_sort) {
                FilterSortProductListDialog dialog = new FilterSortProductListDialog(viewModel.getPriceRange(), viewModel.getSortOption(), ((priceRange, selectedSortOption) -> {
                    viewModel.setFilterSorting(priceRange, selectedSortOption);

                    int filterSortingCount = viewModel.getFilterSortingCount();
                    filterSortBadge.setVisible(filterSortingCount > 0);
                    filterSortBadge.setNumber(filterSortingCount);
                }));

                dialog.show(getSupportFragmentManager(), "FilterSortingDialog");

                return true;
            }

            return false;
        });

        viewModel.getProducts().observe(this, products -> {
            binding.rvProducts.setAdapter(new ProductAdapter(this, products));
        });

        binding.rvProducts.setLayoutManager(new GridLayoutManager(this, 3));
        binding.rvProducts.addItemDecoration(new GridGapItemDecoration(3, 10, false));

        binding.searchBar.setIconifiedByDefault(false);
        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                viewModel.setQuery(query);
                return false;
            }
        });
    }
}