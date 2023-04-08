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
    private final MutableLiveData<Float> mutableTotalPrice;

    public CartRepository() {
        mutableCartItems = new MutableLiveData<>();
        mutableTotalPrice = new MutableLiveData<>(0f);

        List<CartItem> cartItems = MockData.getCartItems();
        mutableCartItems.setValue(cartItems);
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
                calcTotalPrice();
                return;
            }
        }

        cartItems.add(new CartItem(product, 1));
        mutableCartItems.setValue(cartItems);
        calcTotalPrice();
    }

    public void removeItemFromCart(CartItem item) {
        if (mutableCartItems.getValue() == null) {
            return;
        }

        List<CartItem> cartItems = mutableCartItems.getValue();
        cartItems.remove(item);
        mutableCartItems.setValue(cartItems);
        calcTotalPrice();
    }

    public int getCartItemCount() {
        if (mutableCartItems.getValue() == null) {
            return 0;
        }

        return mutableCartItems.getValue().size();
    }

    public void clearCart() {
        mutableCartItems.setValue(new ArrayList<>());
        mutableTotalPrice.setValue(0f);
    }

    public float getTotalPrice() {
        if (mutableTotalPrice.getValue() == null) {
            return 0f;
        }

        return mutableTotalPrice.getValue();
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
                calcTotalPrice();
                Log.i("CartRepository", "changeCartItemQuantity: " + quantity);
                return;
            }
        }
    }

    private void calcTotalPrice() {
        if (mutableCartItems.getValue() == null) {
            return;
        }

        float totalPrice = 0f;

        List<CartItem> cartItems = mutableCartItems.getValue();

        for (var cartItem : cartItems) {
            totalPrice += cartItem.getSubtotal();
        }

        mutableTotalPrice.setValue(totalPrice);
    }
}
