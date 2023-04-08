package com.group05.emarket.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.group05.emarket.MockData;
import com.group05.emarket.models.CartItem;
import com.group05.emarket.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CartRepository {
    private final MutableLiveData<List<CartItem>> mutableCartItems;
    private static CartRepository instance;

    public static CartRepository getInstance() {
        if (instance == null) {
            instance = new CartRepository();
        }
        return instance;
    }


    private CartRepository() {
        mutableCartItems = new MutableLiveData<>(MockData.getCartItems());
    }

    public LiveData<List<CartItem>> getCartItems() {
        return mutableCartItems;
    }

    public void addItemToCart(Product product) {
        if (mutableCartItems.getValue() == null) {
            return;
        }

        List<CartItem> cartItems = mutableCartItems.getValue();

        for (var cartItem : cartItems) {
            if (cartItem.getProduct().getId().equals(product.getId())) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                mutableCartItems.setValue(cartItems);
                return;
            }
        }

        cartItems.add(new CartItem(product, 1));
        mutableCartItems.setValue(cartItems);
    }

    public void removeItemFromCart(CartItem item) {
        if (mutableCartItems.getValue() == null) {
            return;
        }

        List<CartItem> cartItems = mutableCartItems.getValue();
        cartItems.remove(item);
        mutableCartItems.setValue(cartItems);
    }

    public int getCartItemCount() {
        if (mutableCartItems.getValue() == null) {
            return 0;
        }

        return mutableCartItems.getValue().size();
    }

    public void clearCart() {
        if (mutableCartItems.getValue() == null) {
            return;
        }

        var cartItems = mutableCartItems.getValue();
        cartItems.clear();
        mutableCartItems.setValue(cartItems);
    }

    public float getTotalPrice() {
        if (mutableCartItems.getValue() == null) {
            return 0;
        }

        var totalPrice = 0.0f;
        var cartItems = mutableCartItems.getValue();

        for (var cartItem : mutableCartItems.getValue()) {
            totalPrice += cartItem.getSubtotal();
        }

        return totalPrice;
    }

    public void changeCartItemQuantity(CartItem item, int quantity) {
        if (mutableCartItems.getValue() == null) {
            return;
        }

        List<CartItem> cartItems = mutableCartItems.getValue();

        for (var cartItem : cartItems) {
            if (cartItem.getProduct().getId().equals(item.getProduct().getId())) {
                cartItem.setQuantity(quantity);
                mutableCartItems.setValue(cartItems);
                return;
            }
        }
    }
}
