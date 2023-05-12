package com.group05.emarket.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.group05.emarket.models.Product;
import com.group05.emarket.repositories.FavoriteRepository;

import java.util.ArrayList;
import java.util.List;

public class FavoriteViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isLoading;
    private final FavoriteRepository favoriteRepo;
    private final MutableLiveData<List<Product>> favoriteProducts;

    public FavoriteViewModel() {
        this.isLoading = new MutableLiveData<>(false);
        this.favoriteRepo = FavoriteRepository.getInstance();
        this.favoriteProducts = new MutableLiveData<>(new ArrayList<>());
        fetchFavoriteProducts();
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public LiveData<List<Product>> getFavoriteProducts() {
        return favoriteProducts;
    }

    public void fetchFavoriteProducts() {

        isLoading.postValue(true);

        var favoriteProductsFuture = favoriteRepo.getUserFavoriteProducts();

        favoriteProductsFuture.thenAccept(products -> {
            favoriteProducts.setValue(products);
            isLoading.postValue(false);
        });
    }

    public void addFavoriteProduct(Product product) {
        if (isLoading.getValue() == null || isLoading.getValue()) {
            return;
        }

        isLoading.postValue(true);

        var addFavoriteProductFuture = favoriteRepo.addProductToFavorite(product.getId());

        addFavoriteProductFuture.thenAccept(isSuccess -> {
            if (isSuccess) {
                fetchFavoriteProducts();
            }

            isLoading.postValue(false);
        });
    }

    public void removeFavoriteProduct(Product product) {
        if (isLoading.getValue() == null || isLoading.getValue()) {
            return;
        }

        isLoading.postValue(true);

        var removeFavoriteProductFuture = favoriteRepo.removeProductFromFavorite(product.getId());

        removeFavoriteProductFuture.thenAccept(isSuccess -> {
            if (isSuccess) {
                fetchFavoriteProducts();
            }

            isLoading.postValue(false);
        });
    }

    // check if product is in favorite list
    public boolean isFavorite(Product product) {
        var favoriteProducts = this.favoriteProducts.getValue();

        Log.d("FavoriteViewModel", "isFavorite: " + favoriteProducts);

        if (favoriteProducts == null) {
            return false;
        }

        for (var favoriteProduct : favoriteProducts) {
            if (favoriteProduct.getId() == product.getId()) {
                return true;
            }
        }

        return false;
    }

    public static class Factory implements ViewModelProvider.Factory {
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new FavoriteViewModel();
        }
    }
}
