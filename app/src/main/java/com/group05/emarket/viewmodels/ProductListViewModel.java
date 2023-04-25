package com.group05.emarket.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.group05.emarket.Constants;
import com.group05.emarket.enums.EProductListType;
import com.group05.emarket.enums.ESortProductOption;
import com.group05.emarket.models.Product;
import com.group05.emarket.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ProductListViewModel extends ViewModel {
    private final ProductRepository productRepo;
    private final EProductListType type;
    private final String categoryId;
    private final MutableLiveData<List<Product>> products;
    private final MutableLiveData<Boolean> isLoading;
    private float[] priceRange = Constants.DEFAULT_FILTER_PRODUCT_PRICE_RANGE;
    private ESortProductOption sortOption = Constants.DEFAULT_SORT_PRODUCT_OPTION;
    private String query = "";
    private boolean isLastPageReached = false;
    private int currentPage = 1;
    private CompletableFuture<List<Product>> currentFetchTask;
    private boolean isEnableQuery = true;
    private boolean isForceFetch = true;

    public ProductListViewModel(EProductListType type, String categoryId) {
        this.productRepo = ProductRepository.getInstance();
        this.type = type;
        this.categoryId = categoryId;
        this.products = new MutableLiveData<>(new ArrayList<>());
        this.isLoading = new MutableLiveData<>(false);

        fetchProducts();
    }

    public LiveData<List<Product>> getProducts() {
        return products;
    }

    public void setIsEnableQuery(boolean isEnableQuery) {
        this.isEnableQuery = isEnableQuery;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public float[] getPriceRange() {
        return priceRange;
    }

    public ESortProductOption getSortOption() {
        return sortOption;
    }

    public void setQuery(String query) {
        this.query = query;
        currentPage = 1;
        isLastPageReached = false;
        isForceFetch = true;

        if (currentFetchTask != null && !currentFetchTask.isDone()) {
            currentFetchTask.cancel(true);
        }

        fetchProducts();
    }

    public void setFilterSorting(float[] priceRange, ESortProductOption sortOption) {
        this.priceRange = priceRange;
        this.sortOption = sortOption;
        currentPage = 1;
        isLastPageReached = false;
        isForceFetch = true;

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

    public void fetchProducts() {
        if (isLoading.getValue() == null || (isLoading.getValue() && !isForceFetch) || isLastPageReached) {
            return;
        }

        isLoading.setValue(true);
        isForceFetch = false;

        CompletableFuture<List<Product>> future;

        int limit = currentPage * Constants.PRODUCT_ITEM_PER_PAGE;

        if (type == EProductListType.SEARCH) {
            future = productRepo.getProducts(categoryId);
        } else if (type == EProductListType.POPULAR) {
            future = productRepo.getPopularProducts(limit);
        } else if (type == EProductListType.NEW) {
            future = productRepo.getNewProducts(limit);
        } else if (type == EProductListType.DISCOUNT) {
            future = productRepo.getDiscountProducts(limit);
        } else {
            future = productRepo.getProducts(categoryId);
        }

        currentFetchTask = future;

        future.thenAccept(products -> {
            if (isEnableQuery) {
                products = products.stream().filter(product -> {
                    float price = product.getPrice();

                    return price >= priceRange[0]
                            && price <= priceRange[1]
                            && product.getName().toLowerCase().contains(query.toLowerCase());
                }).collect(Collectors.toList());

                if (sortOption == ESortProductOption.NAME_ASCENDING) {
                    products.sort(Comparator.comparing(Product::getName));
                } else if (sortOption == ESortProductOption.NAME_DESCENDING) {
                    products.sort(Comparator.comparing(Product::getName).reversed());
                } else if (sortOption == ESortProductOption.PRICE_ASCENDING) {
                    products.sort(Comparator.comparing(Product::getDiscountedPrice));
                } else if (sortOption == ESortProductOption.PRICE_DESCENDING) {
                    products.sort(Comparator.comparing(Product::getDiscountedPrice).reversed());
                } else if (sortOption == ESortProductOption.HIGHEST_RATED) {
                    products.sort(Comparator.comparing(Product::getAvgRating).reversed());
                } else if (sortOption == ESortProductOption.LOWEST_RATED) {
                    products.sort(Comparator.comparing(Product::getAvgRating));
                }
            }

            products = products.subList(0, Math.min(products.size(), limit));

            if (products.size() < limit) {
                isLastPageReached = true;
            }

            this.products.postValue(products);

            currentPage++;
            isLoading.postValue(false);
        }).exceptionally(e -> {
            isLoading.postValue(false);
            return null;
        });
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final EProductListType type;
        private final String categoryId;

        public Factory(EProductListType type, String categoryId) {
            this.type = type;
            this.categoryId = categoryId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ProductListViewModel(type, categoryId);
        }
    }
}
