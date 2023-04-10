package com.group05.emarket.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.group05.emarket.models.Review;
import com.group05.emarket.repositories.ReviewRepository;

import java.util.List;
import java.util.UUID;

public class ReviewViewModel extends ViewModel {
    private final ReviewRepository reviewRepo = ReviewRepository.getInstance();
    private final MutableLiveData<List<Review>> reviews;
    private final UUID productId;

    private ReviewViewModel(UUID productId) {
        this.productId = productId;
        reviews = new MutableLiveData<>(reviewRepo.getReviews(productId));
    }

    public void filterReviewsByRating(int rating) {
        if (rating == 0) {
            reviews.setValue(reviewRepo.getReviews(productId));
            return;
        }

        reviews.setValue(reviewRepo.getReviewsByRating(productId, rating));
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
