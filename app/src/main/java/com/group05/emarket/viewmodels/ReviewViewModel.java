package com.group05.emarket.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.group05.emarket.Constants;
import com.group05.emarket.models.Review;
import com.group05.emarket.repositories.ReviewRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReviewViewModel extends ViewModel {
    private final ReviewRepository reviewRepo = ReviewRepository.getInstance();
    private final MutableLiveData<List<Review>> reviews;
    private final UUID productId;

    private final MutableLiveData<Boolean> isLoading;
    private boolean isLastPageReached = false;
    private int currentPage = 1;
    private int totalPageCount = 0;

    private ReviewViewModel(UUID productId) {
        this.productId = productId;
        isLoading = new MutableLiveData<>(false);
        reviews = new MutableLiveData<>(new ArrayList<>());

        reviewRepo.getReviewPageCount(productId, Constants.REVIEW_ITEMS_PER_PAGE)
                .thenAccept(pageCount -> {
                    totalPageCount = pageCount;
                    fetchReviews();
                })
                .exceptionally(throwable -> {
                    Log.e("ReviewViewModel", "Failed to get review page count", throwable);
                    return null;
                });
    }

    public void fetchReviews() {
        if (isLoading.getValue() == null || isLoading.getValue() || isLastPageReached) {
            return;
        }

        isLoading.setValue(true);

        reviewRepo.getReviews(productId, currentPage, Constants.REVIEW_ITEMS_PER_PAGE)
                .thenAccept(reviews -> {
                    List<Review> currentReviews = this.reviews.getValue();

                    if (currentReviews != null) {
                        currentReviews.addAll(reviews);
                        Log.d("ReviewViewModel", "Loaded " + reviews.size() + " reviews");
                        this.reviews.postValue(currentReviews);
                    }

                    if (currentPage == totalPageCount) {
                        isLastPageReached = true;
                    }

                    currentPage++;
                    isLoading.postValue(false);
                })
                .exceptionally(throwable -> {
                    Log.e("ReviewViewModel", "Failed to load more reviews", throwable);
                    isLoading.postValue(false);
                    return null;
                });
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public LiveData<List<Review>> getReviews() {
        return reviews;
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final UUID productId;

        public Factory(UUID productId) {
            this.productId = productId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ReviewViewModel(productId);
        }
    }
}
