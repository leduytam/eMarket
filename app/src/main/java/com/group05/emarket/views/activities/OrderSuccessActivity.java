package com.group05.emarket.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.group05.emarket.R;
import com.group05.emarket.models.Order;
import com.group05.emarket.repositories.OrderRepository;
import com.group05.emarket.viewmodels.OrderViewModel;

public class OrderSuccessActivity extends AppCompatActivity {
    private Order order;
    private OrderViewModel orderViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);
        Intent intent = getIntent();
        String orderId = intent.getStringExtra("orderId");
        orderViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new OrderViewModel.Factory(Order.OrderStatus.PENDING)).get(OrderViewModel.class);
        Button btnTrackOrder = findViewById(R.id.btn_track_order);
        btnTrackOrder.setOnClickListener(v -> {
            if (order == null) {
                return;
            }
            Intent intent1 = new Intent(this, OrderDetailActivity.class);
            intent1.putExtra("orderId", order.getId());
            intent1.putExtra("orderStatus", order.getStatus().toString());
            intent1.putExtra("userPhone", order.getPhone());
            intent1.putExtra("userAddress", order.getAddress());
            intent1.putExtra("totalPrice", order.getTotalPrice());
            intent1.putExtra("isReviewed", order.getIsReviewed());
            intent1.putExtra("discount", order.getDiscount());
            startActivity(intent1);
        });

        orderViewModel.getOrders().observe(this, orders -> {
            for (Order order : orders) {
                if (order.getId().equals(orderId)) {
                    this.order = order;
                    break;
                }
            }
        });


        Button btnBackToHome = findViewById(R.id.btn_back_to_home);
        btnBackToHome.setOnClickListener(v -> {
            Intent intent2 = new Intent(this, LayoutActivity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent2);
        });
    }

    // ON RESUME
    @Override
    protected void onResume() {
        super.onResume();
        orderViewModel.fetch();
    }
}