package com.group05.emarket.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.group05.emarket.models.Address;
import com.group05.emarket.models.DeliveryMan;
import com.group05.emarket.models.Order;
import com.group05.emarket.models.OrderProduct;
import com.group05.emarket.repositories.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class OrderDetailViewModel extends ViewModel {
    private static final OrderRepository orderRepository = OrderRepository.getInstance();
    private final MutableLiveData<List<OrderProduct>> products;
    private final MutableLiveData<Boolean> isLoading;
    private final MutableLiveData<DeliveryMan> deliveryMan;

    private final MutableLiveData<Address> orderAddress;

    private final String orderId;

    public OrderDetailViewModel(String orderId) {
        products = new MutableLiveData<>(new ArrayList<>());
        isLoading = new MutableLiveData<>(false);
        deliveryMan = new MutableLiveData<>(new DeliveryMan());
        orderAddress = new MutableLiveData<>(new Address());
        this.orderId = orderId;
        fetchProducts();
    }

    public LiveData<List<OrderProduct>> getProducts() {
        return products;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public void fetchProducts() {
        isLoading.setValue(true);
        orderRepository.getOrderProductDetail(orderId).thenAccept(products -> {
            this.products.setValue(products);
            isLoading.setValue(false);
        }).exceptionally(throwable -> {
            isLoading.setValue(false);
            return null;
        });

        orderRepository.getDeliveryman(orderId).thenAccept(deliveryMan -> {
            this.deliveryMan.setValue(deliveryMan);
            isLoading.setValue(false);
        }).exceptionally(throwable -> {
            isLoading.setValue(false);
            return null;
        });

        orderRepository.getOrderAddress(orderId).thenAccept(address -> {
            this.orderAddress.setValue(address);
            isLoading.setValue(false);
        }).exceptionally(throwable -> {
            isLoading.setValue(false);
            return null;
        });
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final String orderId;

        public Factory(String orderId) {
            this.orderId = orderId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new OrderDetailViewModel(orderId);
        }
    }

    public CompletableFuture<Void> cancelOrder() {
        return orderRepository.setOrderStatus(orderId, Order.OrderStatus.CANCELLED);
    }

    public CompletableFuture<Void> submitReview(String orderId, String review, float rating) {
        return orderRepository.submitReview(orderId, products, review, rating);
    }

    public LiveData<DeliveryMan> getDeliveryMan() {
        return deliveryMan;
    }

    public LiveData<Address> getOrderAddress() {
        return orderAddress;
    }
}
