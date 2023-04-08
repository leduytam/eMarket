package com.group05.emarket.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.group05.emarket.MockData;
import com.group05.emarket.R;
import com.group05.emarket.databinding.ActivityCartBinding;
import com.group05.emarket.models.CartItem;
import com.group05.emarket.viewmodels.CartViewModel;
import com.group05.emarket.views.adapters.CartListAdapter;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartListAdapter.OnCartChangedListener {
    private CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCartBinding binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MaterialToolbar topBar = findViewById(R.id.top_bar);
        topBar.setNavigationOnClickListener(v -> finish());

        CartListAdapter adapter = new CartListAdapter(this);
        binding.rvCartItems.setAdapter(adapter);

        var swipeToDeleteOrderItemCallback = new CartListAdapter.SwipeToDeleteOrderItemCallback(adapter);
        var itemTouchHelper = new ItemTouchHelper(swipeToDeleteOrderItemCallback);
        itemTouchHelper.attachToRecyclerView(binding.rvCartItems);

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.getCartItems().observe(this, cartItems -> {
            adapter.setCartItems(cartItems);
        });
    }

    @Override
    public void onCartItemRemoved(CartItem item) {
        cartViewModel.removeItemFromCart(item);
    }

    @Override
    public void onCartItemQuantityChanged(CartItem item, int quantity) {
        cartViewModel.changeCartItemQuantity(item, quantity);
    }
}