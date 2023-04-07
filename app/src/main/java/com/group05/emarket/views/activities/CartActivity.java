package com.group05.emarket.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.group05.emarket.MockData;
import com.group05.emarket.R;
import com.group05.emarket.databinding.ActivityCartBinding;
import com.group05.emarket.models.CartItem;
import com.group05.emarket.views.adapters.CartListAdapter;

public class CartActivity extends AppCompatActivity implements CartListAdapter.OnCartChangedListener {
    private ActivityCartBinding binding;
    private CartListAdapter.SwipeToDeleteOrderItemCallback swipeToDeleteOrderItemCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MaterialToolbar topBar = findViewById(R.id.top_bar);
        topBar.setNavigationOnClickListener(v -> finish());

        CartListAdapter adapter = new CartListAdapter(this, this);
        binding.rvCartItems.setAdapter(adapter);

        swipeToDeleteOrderItemCallback = new CartListAdapter.SwipeToDeleteOrderItemCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteOrderItemCallback);
        itemTouchHelper.attachToRecyclerView(binding.rvCartItems);

        adapter.submitList(MockData.getCartItems());
    }

    @Override
    public void onCartItemDeleted(CartItem item) {
        Toast.makeText(this, item.getProduct().getName() + " is deleted", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCartItemQuantityChanged(CartItem item, int count) {
        Toast.makeText(this, "Quantity's changed to " + count, Toast.LENGTH_LONG).show();
    }
}