package com.group05.emarket.views.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
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


public class OrderDetailActivity extends AppCompatActivity implements ReviewDialog.ReviewDialogListener {
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
        var totalPrice = extras.getDouble("totalPrice");
        var isReviewed = extras.getBoolean("isReviewed");
        var discount = extras.getInt("discount", 0);

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
        binding.orderDiscount.setText(String.format("Discount: %d%%", discount));
        binding.totalPrice.setText(String.format("Total Price: $%.2f", totalPrice));
        binding.orderId.setText("Order ID: " + orderId);
        binding.orderStatus.setText("Status: " + status);


        viewModel.getDeliveryMan().observe(this, deliveryMan -> {
            if (deliveryMan != null && deliveryMan.getId() != null) {
                binding.orderDeliverymenInfoContainer.setVisibility(View.VISIBLE);
                binding.tvDeliverymenName.setText(deliveryMan.getName());
                binding.tvDeliverymenPhone.setText(deliveryMan.getPhone());
            } else {
                binding.orderDeliverymenInfoContainer.setVisibility(View.GONE);
            }
        });

        viewModel.getOrderAddress().observe(this, address -> {
            if (address != null) {
                binding.userAddress.setText(address.getAddress());
            } else {
                binding.userAddress.setText("This order has no address");
            }
        });

        if (Order.OrderStatus.valueOf(status) == Order.OrderStatus.PENDING) {
            binding.btnFunction.setText("Cancel Order");
            binding.btnFunction.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("Cancel Order")
                        .setMessage("Are you sure you want to cancel this order?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            viewModel.cancelOrder();
                            Toast.makeText(this, "Cancel order successfully", Toast.LENGTH_SHORT).show();
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
        } else if (Order.OrderStatus.valueOf(status) == Order.OrderStatus.DELIVERING) {
            binding.btnFunction.setText("Track Order Location");
            binding.btnFunction.setOnClickListener(v -> {
                Intent intent = new Intent(this, OrderMapActivity.class);
                intent.putExtra("orderLat", viewModel.getOrderAddress().getValue().getLatitude());
                intent.putExtra("orderLng", viewModel.getOrderAddress().getValue().getLongitude());
                intent.putExtra("deliveryLat", viewModel.getDeliveryAddress().getValue().getLatitude());
                intent.putExtra("deliveryLng", viewModel.getDeliveryAddress().getValue().getLongitude());
                startActivity(intent);
            });

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