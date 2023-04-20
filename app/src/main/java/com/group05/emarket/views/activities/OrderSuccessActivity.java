package com.group05.emarket.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.group05.emarket.R;

public class OrderSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        Button btnTrackOrder = findViewById(R.id.btn_track_order);
        btnTrackOrder.setOnClickListener(v -> {
            finish();
        });

        Button btnBackToHome = findViewById(R.id.btn_back_to_home);
        btnBackToHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, LayoutActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}