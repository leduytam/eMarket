package com.group05.emarket.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.group05.emarket.models.Product;
import com.group05.emarket.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailViewModel extends ViewModel {
    private ProductRepository productRepo = ProductRepository.getInstance();
    private Product product;
    private MutableLiveData<Integer> quantity;
    private final MutableLiveData<Boolean> isLoading;
    private final MutableLiveData<List<Product>> overviewRelatedProducts;

    public ProductDetailViewModel(Product product) {
        this.product = product;
        this.quantity = new MutableLiveData<>(1);
        this.isLoading = new MutableLiveData<>(false);
        this.overviewRelatedProducts = new MutableLiveData<>(new ArrayList<>());

        fetchOverviewRelatedProducts();
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LiveData<Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.setValue(quantity);
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<List<Product>> getOverviewRelatedProducts() {
        return overviewRelatedProducts;
    }

    public void fetchOverviewRelatedProducts() {
        if (isLoading.getValue() == null || isLoading.getValue()) {
            return;
        }

        isLoading.postValue(true);

        var overviewRelatedProductsFuture = productRepo.getProducts(product.getCategory().getId(), 4);

        overviewRelatedProductsFuture.thenAccept(products -> {
            products.removeIf(p -> p.getId().equals(product.getId()));

            if (products.size() == 4) {
                products.remove(products.size() - 1);
            }

            overviewRelatedProducts.postValue(products);
            isLoading.postValue(false);
        }).exceptionally(throwable -> {
            isLoading.postValue(false);
            return null;
        });
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final Product product;

        public Factory(Product product) {
            this.product = product;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ProductDetailViewModel(product);
        }
    }
}
