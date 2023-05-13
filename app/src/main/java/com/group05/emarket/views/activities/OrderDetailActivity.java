package com.group05.emarket.views.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.group05.emarket.R;
import com.group05.emarket.databinding.ActivityCartBinding;
import com.group05.emarket.databinding.ActivityOrderDetailBinding;
import com.group05.emarket.models.Order;
import com.group05.emarket.viewmodels.OrderDetailViewModel;
import com.group05.emarket.views.adapters.OrderDetailAdapter;
import com.group05.emarket.views.dialogs.ReviewDialog;


public class OrderDetailActivity extends AppCompatActivity implements ReviewDialog.ReviewDialogListener{

    private OrderDetailViewModel viewModel;
    private ActivityOrderDetailBinding binding;

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MaterialToolbar topBar = binding.topBar;
        topBar.setNavigationOnClickListener(v -> finish());

        var extras = getIntent().getExtras();
        var orderId = extras.getString("orderId");
        var status = extras.getString("orderStatus");
        var userPhone = extras.getString("userPhone");
        var userAddress = extras.getString("userAddress");
        var totalPrice = extras.getDouble("totalPrice");
        var isReviewed = extras.getBoolean("isReviewed");

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
        binding.userPhone.setText("Phone: " + userPhone);
        binding.userAddress.setText(userAddress);
        binding.totalPrice.setText(String.format("Total Price: $%.2f", totalPrice));
        binding.orderId.setText("Order ID: " + orderId);
        binding.orderStatus.setText("Status: " + status);

        if(Order.OrderStatus.valueOf(status) == Order.OrderStatus.PENDING) {
            binding.btnFunction.setText("Cancel Order");
            binding.btnFunction.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("Cancel Order")
                        .setMessage("Are you sure you want to cancel this order?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            viewModel.cancelOrder();
                            Toast.makeText(this, "Cancel order successfullt", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .setNegativeButton("No", null)
                        .show();
            });
        } else if (Order.OrderStatus.valueOf(status) == Order.OrderStatus.DELIVERED) {
            if (isReviewed) {
                binding.btnFunction.setText("Reviewed");
                binding.btnFunction.setEnabled(false);
            } else {
                binding.btnFunction.setText("Review");
                binding.btnFunction.setOnClickListener(v -> {
                    ReviewDialog dialog = new ReviewDialog(viewModel, orderId);
                    dialog.show(getSupportFragmentManager(), "ReviewDialog");
                });
            }
        } else {
            binding.btnFunction.setVisibility(binding.btnFunction.GONE);
        }
    }


    @Override
    public void onReviewSubmit() {
        binding.btnFunction.setText("Reviewed");
        binding.btnFunction.setEnabled(false);
    }
}