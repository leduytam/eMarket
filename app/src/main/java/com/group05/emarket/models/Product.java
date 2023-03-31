package com.group05.emarket.models;

public class Product {
    private String _name;
    private int _image;
    private float _rating;
    private float _price;
    private int _ratingCount;

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

    private Product() {
    }

    public static class Builder {
        private String _name;
        private int _image;
        private float _rating;
        private float _price;
        private int _ratingCount;

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

        public Product build() {
            Product product = new Product();

            product._name = _name;
            product._image = _image;
            product._rating = _rating;
            product._price = _price;
            product._ratingCount = _ratingCount;

            return product;
        }
    }
}
