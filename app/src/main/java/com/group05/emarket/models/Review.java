package com.group05.emarket.models;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class Review {
    @DocumentId
    private String id;
    private String content;
    private float rating;
    private Date createdAt;
    private Date updatedAt;

    // temporary
    private String reviewerName;
    private int reviewerAvatar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public int getReviewerAvatar() {
        return reviewerAvatar;
    }

    public void setReviewerAvatar(int reviewerAvatar) {
        this.reviewerAvatar = reviewerAvatar;
    }

    public Review() {
    }
}
