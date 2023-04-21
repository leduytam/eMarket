package com.group05.emarket.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.group05.emarket.Constants;
import com.group05.emarket.MockData;
import com.group05.emarket.enums.SortProductOption;
import com.group05.emarket.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ProductListCategoryViewModel extends ViewModel {
    private final UUID categoryId;
    private final MutableLiveData<List<Product>> products;
    private final MutableLiveData<Boolean> isLoading;
    private float[] priceRange;
    private SortProductOption sortOption;
    private String query;

    public ProductListCategoryViewModel(UUID categoryId) {
        this.categoryId = categoryId;
        this.products = new MutableLiveData<>(new ArrayList<>());
        this.isLoading = new MutableLiveData<>(false);

        this.priceRange = Constants.DEFAULT_FILTER_PRODUCT_PRICE_RANGE;
        this.sortOption = Constants.DEFAULT_SORT_PRODUCT_OPTION;
        this.query = "";

        fetchProducts();
    }

    public LiveData<List<Product>> getProducts() {
        return products;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public float[] getPriceRange() {
        return priceRange;
    }

    public SortProductOption getSortOption() {
        return sortOption;
    }

    public void setQuery(String query) {
        this.query = query;
        fetchProducts();
    }

    public void setFilterSorting(float[] priceRange, SortProductOption sortOption) {
        this.priceRange = priceRange;
        this.sortOption = sortOption;
        fetchProducts();
    }

    public int getFilterSortingCount() {
        int count = 0;

        if (priceRange[0] != Constants.DEFAULT_FILTER_PRODUCT_PRICE_RANGE[0]
                || priceRange[1] != Constants.DEFAULT_FILTER_PRODUCT_PRICE_RANGE[1]) {
            count++;
        }

        if (sortOption != Constants.DEFAULT_SORT_PRODUCT_OPTION) {
            count++;
        }

        return count;
    }

    private void fetchProducts() {
        isLoading.setValue(true);

        CompletableFuture<List<Product>> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return MockData.getProducts(query, categoryId, priceRange, sortOption);
        });

        future.thenAccept(products -> {
            this.products.postValue(products);
            isLoading.postValue(false);
        }).exceptionally(throwable -> {
            isLoading.postValue(false);
            Log.e("ProductListCategoryViewModel", "Error fetching products", throwable);
            return null;
        });
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final UUID categoryId;

        public Factory(UUID categoryId) {
            this.categoryId = categoryId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ProductListCategoryViewModel(categoryId);
        }
    }
}
