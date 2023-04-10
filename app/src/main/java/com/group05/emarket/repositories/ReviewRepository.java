package com.group05.emarket.repositories;

import com.group05.emarket.MockData;
import com.group05.emarket.models.Review;

import java.util.List;
import java.util.UUID;

public class ReviewRepository {
    private static ReviewRepository instance;

    public static ReviewRepository getInstance() {
        if (instance == null) {
            instance = new ReviewRepository();
        }

        return instance;
    }

    private ReviewRepository() {
    }

    public List<Review> getReviews(UUID productId) {
        return MockData.getReviews();
    }

    public List<Review> getReviewsByRating(UUID productId, int rating) {
        return null;
    }
}
