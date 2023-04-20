package com.group05.emarket.models;

import java.util.UUID;

public class Product {

    protected UUID id;
    protected String documentId;
    protected String name;
    protected int image;
    protected float price;
    protected float avgRating;
    protected int ratingCount;
    protected int discount;
    protected String description;
    protected String weightUnit;
    protected float weight;
    protected Category category;
    protected String imageUrl;
    private String categoryUuid;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public float getAvgRating() {
        return avgRating;
    }

    public float getPrice() {
        return price;
    }

    public float getDiscountedPrice() {
        return price * (1.0f - discount / 100f);
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public int getDiscount() {
        return discount;
    }

    public String getDescription() {
        return description;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public float getWeight() {
        return weight;
    }

    public Category getCategory() {
        return category;
    }

    public String getDocumentId() {
        return documentId;
    }

    private Product() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategoryUuid() {
        return categoryUuid;
    }

    public static class Builder {
        private UUID id;
        private String name;
        private int image;
        private float avgRating;
        private float price;
        private int ratingCount;
        private int discount;
        private String description;

        private float weight;

        private String weightUnit;
        private String imageUrl;
        private String categoryUuid;
        private String documentId;

        public Builder setCategoryUuid(String categoryUuid) {
            this.categoryUuid = categoryUuid;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setDocumentId(String documentId) {
            this.documentId = documentId;
            return this;
        }

        public Builder setId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setImage(int image) {
            this.image = image;
            return this;
        }

        public Builder setAvgRating(float avgRating) {
            this.avgRating = avgRating;
            return this;
        }

        public Builder setPrice(float price) {
            this.price = price;
            return this;
        }

        public Builder setRatingCount(int reviewCount) {
            ratingCount = reviewCount;
            return this;
        }

        public Builder setDiscount(int discount) {
            this.discount = discount;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setWeight(float weight) {
            this.weight = weight;
            return this;
        }

        public Builder setWeightUnit(String weightUnit) {
            this.weightUnit = weightUnit;
            return this;
        }

        public Product build() {
            Product product = new Product();

            product.id = id;
            product.documentId = documentId;
            product.name = name;
            product.image = image;
            product.avgRating = avgRating;
            product.price = price;
            product.ratingCount = ratingCount;
            product.discount = discount;
            product.description = description;
            product.weight = weight;
            product.weightUnit = weightUnit;
            product.category = null;
            product.imageUrl = imageUrl;
            product.categoryUuid = categoryUuid;

            return product;
        }
    }
}
