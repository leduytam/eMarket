package com.group05.emarket.models;

import java.util.UUID;

public class Product {
    private UUID _id;
    private String _name;
    private int _image;
    private float _rating;
    private float _price;
    private int _ratingCount;
    private int discount;

    public UUID getId() { return _id; }

    public String getName() {
        return _name;
    }

    public int getImage() {
        return _image;
    }

    public float getRating() {
        return _rating;
    }

    public float getPrice() {
        return _price;
    }

    public int getRatingCount() {
        return _ratingCount;
    }

    public int getDiscount() { return discount; }

    private Product() {
    }

    public static class Builder {
        private UUID _id;
        private String _name;
        private int _image;
        private float _rating;
        private float _price;
        private int _ratingCount;
        private int discount;

        public Builder setId(UUID id) {
            _id = id;
            return this;
        }

        public Builder setName(String name) {
            _name = name;
            return this;
        }

        public Builder setImage(int image) {
            _image = image;
            return this;
        }

        public Builder setRating(float rating) {
            _rating = rating;
            return this;
        }

        public Builder setPrice(float price) {
            _price = price;
            return this;
        }

        public Builder setRatingCount(int reviewCount) {
            _ratingCount = reviewCount;
            return this;
        }

        public Builder setDiscount(int discount) {
            this.discount = discount;
            return this;
        }

        public Product build() {
            Product product = new Product();

            product._id = _id;
            product._name = _name;
            product._image = _image;
            product._rating = _rating;
            product._price = _price;
            product._ratingCount = _ratingCount;
            product.discount = discount;

            return product;
        }
    }
}
