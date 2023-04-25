package com.group05.emarket.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.group05.emarket.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAboutBinding binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.topBar.setNavigationOnClickListener(v -> finish());
    }
}