package com.group05.emarket.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.group05.emarket.Constants;
import com.group05.emarket.enums.SortProductOption;
import com.group05.emarket.models.Product;
import com.group05.emarket.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ProductListCategoryViewModel extends ViewModel {
    private final ProductRepository productRepo;
    private final UUID categoryId;
    private final MutableLiveData<List<Product>> products;
    private final MutableLiveData<Boolean> isLoading;
    private float[] priceRange = Constants.DEFAULT_FILTER_PRODUCT_PRICE_RANGE;
    private SortProductOption sortOption = Constants.DEFAULT_SORT_PRODUCT_OPTION;
    private String query = "";
    private boolean isLastPageReached = false;
    private int currentPage = 1;
    private int totalPageCount = 0;
    private CompletableFuture<List<Product>> currentFetchTask;

    public ProductListCategoryViewModel(UUID categoryId) {
        this.productRepo = ProductRepository.getInstance();
        this.categoryId = categoryId;
        this.products = new MutableLiveData<>(new ArrayList<>());
        this.isLoading = new MutableLiveData<>(false);

        productRepo.getProductPageCount(query, categoryId, priceRange, Constants.PRODUCT_ITEM_PER_PAGE)
                .thenAccept(pageCount -> {
                    totalPageCount = pageCount;
                    fetchProducts();
                })
                .exceptionally(throwable -> {
                    Log.e("ProductListCategoryViewModel", "Failed to get product page count", throwable);
                    return null;
                });
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
        currentPage = 1;
        isLastPageReached = false;

        if (currentFetchTask != null && !currentFetchTask.isDone()) {
            currentFetchTask.cancel(true);
        }

        productRepo.getProductPageCount(query, categoryId, priceRange, Constants.PRODUCT_ITEM_PER_PAGE)
                .thenAccept(pageCount -> {
                    totalPageCount = pageCount;
                    fetchProducts();
                })
                .exceptionally(throwable -> {
                    Log.e("ProductListCategoryViewModel", "Failed to get product page count", throwable);
                    return null;
                });
    }

    public void setFilterSorting(float[] priceRange, SortProductOption sortOption) {
        this.priceRange = priceRange;
        this.sortOption = sortOption;
        currentPage = 1;
        isLastPageReached = false;

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
        if (isLoading.getValue() == null || isLoading.getValue() || isLastPageReached) {
            return;
        }

        isLoading.setValue(true);

        var future = productRepo.getProducts(query, categoryId, priceRange, sortOption, currentPage, Constants.PRODUCT_ITEM_PER_PAGE);

        currentFetchTask = future;

        future.thenAccept(products -> {
            this.products.postValue(products);

            if (currentPage >= totalPageCount) {
                isLastPageReached = true;
            }

            currentPage++;
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
