package com.group05.emarket.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.group05.emarket.databinding.ActivityHelpBinding;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHelpBinding binding = ActivityHelpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.topBar.setNavigationOnClickListener(v -> finish());
    }
}