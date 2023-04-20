package com.group05.emarket.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

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

        binding.topBar.setNavigationOnClickListener(v -> finish());

        var layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rvReviews.setLayoutManager(layoutManager);
        binding.rvReviews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    reviewViewModel.loadMoreReviews();
                }
            }
        });

        reviewViewModel.getReviews().observe(this, reviews -> {
            var scrollPosition = layoutManager.findFirstVisibleItemPosition();
            binding.rvReviews.setAdapter(new ReviewAdapter(this, reviews));
            layoutManager.scrollToPosition(scrollPosition);
        });

        reviewViewModel.isLoading().observe(this, isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });
    }
}