package com.group05.emarket.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group05.emarket.MockData;
import com.group05.emarket.models.CartItem;
import com.group05.emarket.models.Product;
import com.group05.emarket.repositories.CartRepository;

import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends ViewModel {
    private final CartRepository cartRepo = CartRepository.getInstance();
    private final MutableLiveData<List<CartItem>> cartItems;
    private final MutableLiveData<Boolean> isLoading;

    public CartViewModel() {
        cartItems = new MutableLiveData<>(new ArrayList<>());
        isLoading = new MutableLiveData<>(true);

        fetch();
    }

    public void fetch() {
        isLoading.setValue(true);

        cartRepo.getCart().thenAccept(cartItems -> {
            this.cartItems.setValue(cartItems);
            isLoading.setValue(false);
        }).exceptionally(e -> {
            Log.e("CartViewModel", "Error getting cart", e);
            return null;
        });
    }

    public LiveData<List<CartItem>> getCartItems() {
        return cartItems;
    }

    public void addItemToCart(Product product, int quantity) {
        if (cartItems.getValue() == null || quantity <= 0) {
            return;
        }

        List<CartItem> items = cartItems.getValue();

        for (var cartItem : items) {
            if (cartItem.getProduct().getId().equals(product.getId())) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItems.setValue(items);
                updateCart();
                return;
            }
        }

        items.add(new CartItem(product, quantity));
        cartItems.setValue(items);
        updateCart();
    }

    public void removeItemFromCart(CartItem item) {
        if (cartItems.getValue() == null) {
            return;
        }

        List<CartItem> items = cartItems.getValue();
        items.remove(item);

        cartItems.setValue(items);
        updateCart();
    }

    public void clearCart() {
        cartItems.setValue(new ArrayList<>());
        updateCart();
    }

    public float getTotalPrice() {
        if (cartItems.getValue() == null) {
            return 0;
        }

        var totalPrice = 0.0f;

        for (var cartItem : cartItems.getValue()) {
            totalPrice += cartItem.getSubtotal();
        }

        return totalPrice;
    }

    public void changeCartItemQuantity(CartItem item, int quantity) {
        if (cartItems.getValue() == null) {
            return;
        }

        List<CartItem> items = cartItems.getValue();

        for (var cartItem : items) {
            if (cartItem.getProduct().getId().equals(item.getProduct().getId())) {
                cartItem.setQuantity(quantity);
                cartItems.setValue(items);
                updateCart();
                return;
            }
        }
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    private void updateCart() {
        cartRepo.updateCart(cartItems.getValue());
    }
}
