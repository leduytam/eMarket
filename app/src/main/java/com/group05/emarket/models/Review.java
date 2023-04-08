package com.group05.emarket.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Review {
    private UUID id;
    private String content;
    private float rating;
    private LocalDateTime createdAt;
    private String reviewerName;
    private int reviewerAvatar;

    public UUID getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public float getRating() {
        return rating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public int getReviewerAvatar() {
        return reviewerAvatar;
    }

    private Review() {
    }

    public static class Builder {
        private UUID id;
        private String content;
        private float rating;
        private LocalDateTime createdAt;
        private String reviewerName;
        private int reviewerAvatar;

        public Builder setId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setRating(float rating) {
            this.rating = rating;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setReviewerName(String reviewerName) {
            this.reviewerName = reviewerName;
            return this;
        }

        public Builder setReviewerAvatar(int reviewerAvatar) {
            this.reviewerAvatar = reviewerAvatar;
            return this;
        }

        public Review build() {
            Review review = new Review();
            review.id = id;
            review.content = content;
            review.rating = rating;
            review.createdAt = createdAt;
            review.reviewerName = reviewerName;
            review.reviewerAvatar = reviewerAvatar;
            return review;
        }
    }
}
