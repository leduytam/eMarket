package com.group05.emarket.viewmodels;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group05.emarket.models.Category;
import com.group05.emarket.models.Product;
import com.group05.emarket.repositories.CartRepository;
import com.group05.emarket.repositories.CategoryRepository;
import com.group05.emarket.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HomeViewModel extends ViewModel {
    public CategoryRepository categoryRepo = CategoryRepository.getInstance();
    public ProductRepository productRepo = ProductRepository.getInstance();
    private final MutableLiveData<List<Category>> categories;
    private final MutableLiveData<List<Product>> popularProducts;
    private final MutableLiveData<List<Product>> newProducts;
    private final MutableLiveData<List<Product>> discountProducts;
    private final MutableLiveData<Boolean> isLoading;
    private boolean isLoaded;

    public HomeViewModel() {
        categories = new MutableLiveData<>(new ArrayList<>());
        popularProducts = new MutableLiveData<>(new ArrayList<>());
        newProducts = new MutableLiveData<>(new ArrayList<>());
        discountProducts = new MutableLiveData<>(new ArrayList<>());
        isLoading = new MutableLiveData<>(false);
        isLoaded = false;

        fetch();
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    public LiveData<List<Product>> getPopularProducts() { return popularProducts; }

    public LiveData<List<Product>> getNewProducts() { return newProducts; }

    public LiveData<List<Product>> getDiscountProducts() { return discountProducts; }

    public void fetch() {
        if (isLoading.getValue() == null || isLoading.getValue()) {
            return;
        }

        isLoading.postValue(true);

        var categoriesFuture = categoryRepo.getCategories();
        var popularProductsFuture = productRepo.getPopularProducts(3);
        var newProductsFuture = productRepo.getNewProducts(3);
        var discountProductsFuture = productRepo.getDiscountProducts(3);

        var futures = CompletableFuture.allOf(
                categoriesFuture,
                popularProductsFuture,
                newProductsFuture,
                discountProductsFuture
        );

        futures.thenRun(() -> {
            try {
                categories.postValue(categoriesFuture.get());
                popularProducts.postValue(popularProductsFuture.get());
                newProducts.postValue(newProductsFuture.get());
                discountProducts.postValue(discountProductsFuture.get());
                isLoaded = true;
            } catch (Exception e) {
                Log.e("HomeViewModel", "Error while fetching data", e);
            } finally {
                isLoading.postValue(false);
            }
        });
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public boolean isLoaded() {
        return isLoaded;
    }
}
