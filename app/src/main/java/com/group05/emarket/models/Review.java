package com.group05.emarket.models;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.group05.emarket.R;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class Review {
    @DocumentId
    private String id;
    private String content;
    private float rating;
    private Date createdAt;
    private Date updatedAt;
    private Reviewer reviewer;

    public Review() {
    }

    public Review(String id, Map<String, Object> map) {
        this.id = id;
        content = (String) map.get("review");
        rating = ((Number) map.get("rating")).floatValue();
        createdAt = ((Timestamp) map.get("createdAt")).toDate();
        updatedAt = ((Timestamp) map.get("updatedAt")).toDate();
    }

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

    public Reviewer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }

    public static class Reviewer {
        private String id;
        private String name;
        private int avatar;
        public Reviewer() {
        }

        public Reviewer(String id, Map<String, Object> map) {
            this.id = id;
            name = (String) map.get("fullName");
            avatar = R.drawable.ic_default_avatar;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getAvatar() {
            return avatar;
        }
    }
}
