package com.group05.emarket.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.group05.emarket.R;
import com.group05.emarket.databinding.ActivityCategoryBinding;
import com.group05.emarket.enums.EProductListType;
import com.group05.emarket.models.Category;
import com.group05.emarket.viewmodels.ProductListViewModel;
import com.group05.emarket.views.adapters.ProductAdapter;
import com.group05.emarket.views.decorations.GridGapItemDecoration;
import com.group05.emarket.views.dialogs.FilterSortProductListDialog;

public class CategoryActivity extends AppCompatActivity {
    private ProductListViewModel viewModel;

    @SuppressLint("UnsafeOptInUsageError")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        var binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        var extras = getIntent().getExtras();

        if (extras != null) {
            var category = (Category) extras.getParcelable("category");

            if (category == null) {
                var dialog = new MaterialAlertDialogBuilder(this)
                        .setTitle("An error occurred")
                        .setMessage("Category is null")
                        .setPositiveButton("OK", (dialog1, which) -> finish())
                        .setCancelable(false)
                        .setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_dialog_alert, null))
                        .create();

                dialog.show();
                return;
            }

            binding.topBar.setTitle(category.getName());
            viewModel = new ViewModelProvider(this, new ProductListViewModel.Factory(EProductListType.SEARCH, category.getId())).get(ProductListViewModel.class);
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
            Log.d("ProductListCategory", "onCreate: " + products.size());
            binding.tvNoProducts.setVisibility(products.size() == 0 ? View.VISIBLE : View.GONE);
            binding.rvProducts.setVisibility(products.size() == 0 ? View.GONE : View.VISIBLE);
            binding.rvProducts.setAdapter(new ProductAdapter(this, products));
        });

        viewModel.isLoading().observe(this, isLoading -> {
            binding.progressBar.setVisibility(isLoading ? android.view.View.VISIBLE : android.view.View.GONE);
            binding.rvProducts.setVisibility(isLoading ? android.view.View.GONE : android.view.View.VISIBLE);

            if (isLoading || (viewModel.getProducts().getValue() != null && viewModel.getProducts().getValue().size() > 0)) {
                binding.tvNoProducts.setVisibility(View.GONE);
            } else {
                binding.tvNoProducts.setVisibility(View.VISIBLE);
            }
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