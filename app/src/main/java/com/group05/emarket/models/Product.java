package com.group05.emarket.models;

import java.util.UUID;

public class Product {
    protected UUID id;
    protected String name;
    protected int image;
    protected float price;
    protected float avgRating;
    protected int ratingCount;
    protected int discount;
    protected String description;

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

    public int getRatingCount() {
        return ratingCount;
    }

    public int getDiscount() {
        return discount;
    }

    public String getDescription() {
        return description;
    }

    private Product() {
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

        public Product build() {
            Product product = new Product();

            product.id = id;
            product.name = name;
            product.image = image;
            product.avgRating = avgRating;
            product.price = price;
            product.ratingCount = ratingCount;
            product.discount = discount;
            product.description = description;

            return product;
        }
    }
}
