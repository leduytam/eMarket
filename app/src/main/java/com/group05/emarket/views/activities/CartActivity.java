package com.group05.emarket.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.group05.emarket.R;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        MaterialToolbar topBar = findViewById(R.id.top_bar);
        topBar.setNavigationOnClickListener(v -> finish());
    }
}