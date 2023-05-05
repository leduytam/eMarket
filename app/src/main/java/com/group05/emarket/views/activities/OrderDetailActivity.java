package com.group05.emarket.views.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.group05.emarket.R;
import com.group05.emarket.databinding.ActivityCartBinding;
import com.group05.emarket.databinding.ActivityOrderDetailBinding;
import com.group05.emarket.models.Order;
import com.group05.emarket.viewmodels.OrderDetailViewModel;
import com.group05.emarket.views.adapters.OrderDetailAdapter;


public class OrderDetailActivity extends AppCompatActivity {

    private OrderDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOrderDetailBinding binding = ActivityOrderDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MaterialToolbar topBar = binding.topBar;
        topBar.setNavigationOnClickListener(v -> finish());

        var extras = getIntent().getExtras();
        var orderId = extras.getString("orderId");
        var status = extras.getString("orderStatus");
        var userName = extras.getString("userName");
        var userPhone = extras.getString("userPhone");
        var userAddress = extras.getString("userAddress");
        var totalPrice = extras.getDouble("totalPrice");



        viewModel = new ViewModelProvider(this, new OrderDetailViewModel.Factory(orderId)).get(OrderDetailViewModel.class);
        viewModel.fetchProducts();
        RecyclerView recyclerOrdersView = binding.orderInfo.findViewById(R.id.rv_order_items);
        recyclerOrdersView.setLayoutManager(new LinearLayoutManager(this));
        recyclerOrdersView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        viewModel.getProducts().observe(this, products -> {
            recyclerOrdersView.setAdapter(new OrderDetailAdapter(products));
        });
        viewModel.isLoading().observe(this, isLoading -> {
            if (isLoading) {
                binding.orderInfo.setVisibility(View.GONE);
                binding.pbFetchingOrders.setVisibility(View.VISIBLE);
            } else {
                binding.orderInfo.setVisibility(View.VISIBLE);
                binding.pbFetchingOrders.setVisibility(View.GONE);
            }
        });
        binding.userName.setText(userName);
        binding.userPhone.setText("+" + userPhone);
        binding.userAddress.setText(userAddress);
        binding.totalPrice.setText(String.format("Total Price: $%.2f", totalPrice));

        if(Order.OrderStatus.valueOf(status) == Order.OrderStatus.PENDING) {
            binding.btnFunction.setText("Cancel Order");
            binding.btnFunction.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("Cancel Order")
                        .setMessage("Are you sure you want to cancel this order?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            viewModel.cancelOrder();
                            finish();
                        })
                        .setNegativeButton("No", null)
                        .show();
            });
        } else if (Order.OrderStatus.valueOf(status) == Order.OrderStatus.DELIVERED) {
            binding.btnFunction.setText("Review");
        } else {
            binding.btnFunction.setVisibility(binding.btnFunction.GONE);
        }
    }

}