package com.group05.emarket.viewmodels;

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
import java.util.concurrent.CompletableFuture;

public class ReviewViewModel extends ViewModel {
    private final ReviewRepository reviewRepo = ReviewRepository.getInstance();
    private final MutableLiveData<List<Review>> reviews;
    private final MutableLiveData<Boolean> isLoading;
    private final String productId;

    private boolean isLastPageReached = false;
    private int currentPage = 1;

    private ReviewViewModel(String productId) {
        this.productId = productId;
        isLoading = new MutableLiveData<>(false);
        reviews = new MutableLiveData<>(new ArrayList<>());

        fetch();
    }

    public void fetch() {
        if (isLoading.getValue() == null || isLoading.getValue() || isLastPageReached) {
            return;
        }

        isLoading.setValue(true);

        final int limit = currentPage * Constants.REVIEW_ITEMS_PER_PAGE;

        CompletableFuture<List<Review>> future = reviewRepo.getReviews(productId, limit);

        future.thenAccept(reviews -> {
            if (reviews.size() < limit) {
                isLastPageReached = true;
            }

            this.reviews.postValue(reviews);

            currentPage++;
            isLoading.postValue(false);
        }).exceptionally(e -> {
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
        private final String productId;

        public Factory(String productId) {
            this.productId = productId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ReviewViewModel(productId);
        }
    }
}
