package com.group05.emarket.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.group05.emarket.R;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.group05.emarket.R.layout.activity_notification);

        MaterialToolbar topBar = findViewById(R.id.top_bar);
        topBar.setNavigationOnClickListener(v -> finish());
    }
}