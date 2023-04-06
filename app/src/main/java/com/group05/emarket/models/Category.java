package com.group05.emarket.models;

import java.util.UUID;

public class Category {
    private UUID _id;
    private String _name;
    private int _image;

    private Category() {
    }

    public String getName() {
        return _name;
    }

    public int getImage() {
        return _image;
    }

    public UUID getId() {
        return _id;
    }

    public static class Builder {
        private UUID _id;
        private String _name;
        private int _image;

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

        public Category build() {
            var category = new Category();
            category._id = _id;
            category._name = _name;
            category._image = _image;
            return category;
        }
    }
}
