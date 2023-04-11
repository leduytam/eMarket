package com.group05.emarket.models;

import java.util.UUID;

public class Category {
    private UUID id;
    private String name;
    private int image;
    private int background;

    private Category() {
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public UUID getId() {
        return id;
    }

    public int getBackground() {
        return background;
    }

    public static class Builder {
        private UUID id;
        private String name;
        private int image;
        private int background;

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

        public Builder setBackground(int background) {
            this.background = background;
            return this;
        }

        public Category build() {
            var category = new Category();
            category.id = id;
            category.name = name;
            category.image = image;
            category.background = background;
            return category;
        }
    }
}
