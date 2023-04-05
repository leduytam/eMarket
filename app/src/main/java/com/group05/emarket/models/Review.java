package com.group05.emarket.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Review {
    private UUID _id;
    private String _content;
    private float _rating;
    private LocalDateTime _createdAt;
    private String _reviewerName;
    private int _reviewerAvatar;

    public UUID getId() {
        return _id;
    }

    public String getContent() {
        return _content;
    }

    public float getRating() {
        return _rating;
    }

    public LocalDateTime getCreatedAt() {
        return _createdAt;
    }

    public String getReviewerName() {
        return _reviewerName;
    }

    public int getReviewerAvatar() {
        return _reviewerAvatar;
    }

    private Review() {
    }

    public static class Builder {
        private UUID _id;
        private String _content;
        private float _rating;
        private LocalDateTime _createdAt;
        private String _reviewerName;
        private int _reviewerAvatar;

        public Builder setId(UUID id) {
            _id = id;
            return this;
        }

        public Builder setContent(String content) {
            _content = content;
            return this;
        }

        public Builder setRating(float rating) {
            _rating = rating;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            _createdAt = createdAt;
            return this;
        }

        public Builder setReviewerName(String reviewerName) {
            _reviewerName = reviewerName;
            return this;
        }

        public Builder setReviewerAvatar(int reviewerAvatar) {
            _reviewerAvatar = reviewerAvatar;
            return this;
        }

        public Review build() {
            Review review = new Review();
            review._id = _id;
            review._content = _content;
            review._rating = _rating;
            review._createdAt = _createdAt;
            review._reviewerName = _reviewerName;
            review._reviewerAvatar = _reviewerAvatar;
            return review;
        }
    }
}
