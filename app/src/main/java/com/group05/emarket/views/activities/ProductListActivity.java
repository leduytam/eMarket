package com.group05.emarket.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.group05.emarket.R;
import com.group05.emarket.databinding.ActivityCategoryBinding;
import com.group05.emarket.databinding.ActivityProductListBinding;
import com.group05.emarket.enums.EProductListType;
import com.group05.emarket.viewmodels.ProductListViewModel;
import com.group05.emarket.views.adapters.ProductAdapter;
import com.group05.emarket.views.decorations.GridGapItemDecoration;
import com.group05.emarket.views.dialogs.FilterSortProductListDialog;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {
    private ProductListViewModel viewModel;

    @SuppressLint("UnsafeOptInUsageError")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        var binding = ActivityProductListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        var extras = getIntent().getExtras();

        var title = extras.getString("title");
        var type = (EProductListType) extras.getSerializable("type");
        var categoryId = extras.getString("categoryId");
        var isEnableQuery = extras.containsKey("isEnableQuery") && extras.getBoolean("isEnableQuery");
        var isFocusSearch = extras.containsKey("isFocusSearch") && extras.getBoolean("isFocusSearch");

        binding.topBar.setTitle(title);

        viewModel = new ViewModelProvider(this, new ProductListViewModel.Factory(type, categoryId)).get(ProductListViewModel.class);

        if (!isEnableQuery) {
            binding.searchBar.setVisibility(View.GONE);
            binding.topBar.getMenu().findItem(R.id.action_filter_sort).setVisible(false);
        }

        viewModel.setIsEnableQuery(isEnableQuery);

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

        var layoutManager = new GridLayoutManager(this, 3);
        binding.rvProducts.setLayoutManager(layoutManager);
        binding.rvProducts.addItemDecoration(new GridGapItemDecoration(3, 10, false));
        binding.rvProducts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    viewModel.fetchProducts();
                }
            }
        });

        viewModel.getProducts().observe(this, products -> {
            binding.tvNoProducts.setVisibility(products.size() == 0 ? View.VISIBLE : View.GONE);
            binding.rvProducts.setVisibility(products.size() == 0 ? View.GONE : View.VISIBLE);
            var state = binding.rvProducts.getLayoutManager().onSaveInstanceState();
            binding.rvProducts.setAdapter(new ProductAdapter(this, products));
            binding.rvProducts.getLayoutManager().onRestoreInstanceState(state);
        });

        viewModel.isLoading().observe(this, isLoading -> {
            if (viewModel.getProducts().getValue() != null && viewModel.getProducts().getValue().size() > 0) {
                binding.pbFetchingMoreProducts.setVisibility(isLoading ? android.view.View.VISIBLE : android.view.View.GONE);
                binding.rvProducts.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
            } else {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.pbFetchingMoreProducts.setVisibility(View.GONE);
                binding.rvProducts.setVisibility(isLoading ? android.view.View.GONE : android.view.View.VISIBLE);
            }

            if (isLoading || (viewModel.getProducts().getValue() != null && viewModel.getProducts().getValue().size() > 0)) {
                binding.tvNoProducts.setVisibility(View.GONE);
            } else {
                binding.tvNoProducts.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
            }
        });

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

        if (isFocusSearch) {
            binding.searchBar.requestFocus();
        }
    }
}