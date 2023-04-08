package com.group05.emarket.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.group05.emarket.models.CartItem;
import com.group05.emarket.models.Product;
import com.group05.emarket.repositories.CartRepository;

import java.util.List;

public class CartViewModel extends ViewModel {
    private final CartRepository cartRepo;

    public CartViewModel() {
        cartRepo = new CartRepository();
    }

    public LiveData<List<CartItem>> getCartItems() {
        return cartRepo.getCartItems();
    }

    public void addItemToCart(Product product) {
        cartRepo.addItemToCart(product);
    }

    public void removeItemFromCart(CartItem item) {
        cartRepo.removeItemFromCart(item);
    }

    public void clearCart() {
        cartRepo.clearCart();
    }

    public float getTotalPrice() {
        return cartRepo.getTotalPrice();
    }

    public int getSize() {
        return cartRepo.getCartItemCount();
    }

    public void changeCartItemQuantity(CartItem item, int quantity) {
        cartRepo.changeCartItemQuantity(item, quantity);
    }
}
