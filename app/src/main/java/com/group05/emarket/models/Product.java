package com.group05.emarket.models;

import java.util.UUID;

public class Product {
    protected UUID _id;
    protected String _name;
    protected int _image;
    protected float _price;
    protected float _avgRating;
    protected int _ratingCount;
    protected int _discount;
    protected String _description;

    public UUID getId() { return _id; }

    public String getName() {
        return _name;
    }

    public int getImage() {
        return _image;
    }

    public float getAvgRating() {
        return _avgRating;
    }

    public float getPrice() {
        return _price;
    }

    public int getRatingCount() {
        return _ratingCount;
    }

    public int getDiscount() { return _discount; }

    public String getDescription() {
        return _description;
    }

    private Product() {
    }

    public static class Builder {
        private UUID _id;
        private String _name;
        private int _image;
        private float _avgRating;
        private float _price;
        private int _ratingCount;
        private int _discount;
        private String _description;

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

        public Builder setAvgRating(float avgRating) {
            _avgRating = avgRating;
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
            this._discount = discount;
            return this;
        }

        public Builder setDescription(String description) {
            _description = description;
            return this;
        }

        public Product build() {
            Product product = new Product();

            product._id = _id;
            product._name = _name;
            product._image = _image;
            product._avgRating = _avgRating;
            product._price = _price;
            product._ratingCount = _ratingCount;
            product._discount = _discount;
            product._description = _description;

            return product;
        }
    }
}
