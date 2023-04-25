package com.group05.emarket.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.group05.emarket.MockData;
import com.group05.emarket.databinding.ActivityWalletBinding;
import com.group05.emarket.models.Order;
import com.group05.emarket.models.Payment;
import com.group05.emarket.views.adapters.HistoryItemAdapter;
import com.group05.emarket.views.adapters.PaymentItemAdapter;

import java.util.List;

public class WalletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWalletBinding binding = ActivityWalletBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.topBar.setNavigationOnClickListener(v -> finish());

        binding.addPaymentButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddPaymentActivity.class);
            startActivity(intent);
        });

        List<Payment> payments;
        payments = MockData.getPayments();
        binding.rvPayments.setAdapter(new PaymentItemAdapter(this, payments));
        binding.rvPayments.setLayoutManager(new LinearLayoutManager(this));
        binding.rvPayments.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        List<Order> pendingOrders;
        pendingOrders = MockData.getOrders();
        binding.rvHistory.setAdapter(new HistoryItemAdapter(this, pendingOrders));
        binding.rvHistory.setLayoutManager(new LinearLayoutManager(this));
        binding.rvHistory.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}