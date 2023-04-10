package com.group05.emarket.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.badge.ExperimentalBadgeUtils;

@ExperimentalBadgeUtils public class SplashActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashActivity.this, LayoutActivity.class));
                finish();
            }, 1500);
        }
}
