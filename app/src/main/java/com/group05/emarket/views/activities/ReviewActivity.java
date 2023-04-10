package com.group05.emarket.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.group05.emarket.databinding.ActivityReviewBinding;
import com.group05.emarket.viewmodels.ReviewViewModel;
import com.group05.emarket.views.adapters.ReviewAdapter;

import java.util.UUID;

public class ReviewActivity extends AppCompatActivity {
    private ReviewViewModel reviewViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityReviewBinding binding = ActivityReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        UUID productId = (UUID) getIntent().getSerializableExtra("productId");

        reviewViewModel = new ViewModelProvider(this, new ReviewViewModel.Factory(productId)).get(ReviewViewModel.class);

        reviewViewModel.getReviews().observe(this, reviews -> {
            binding.rvReviews.setAdapter(new ReviewAdapter(this, reviews));
        });

        binding.topBar.setNavigationOnClickListener(v -> finish());
    }
}