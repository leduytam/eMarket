package com.group05.emarket.models;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;
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

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;

        Product product = (Product) obj;
        return product.id.equals(id) &&
                product.name.equals(name) &&
                product.image == image &&
                product.avgRating == avgRating &&
                product.price == price &&
                product.ratingCount == ratingCount &&
                product.discount == discount &&
                product.description.equals(description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image, avgRating, price, ratingCount, discount, description);
    }

    public static DiffUtil.ItemCallback<Product> itemCallback = new DiffUtil.ItemCallback<Product>() {
        @Override
        public boolean areItemsTheSame(Product oldItem, Product newItem) {
            return oldItem.id.equals(newItem.id);
        }

        @Override
        public boolean areContentsTheSame(Product oldItem, Product newItem) {
            return oldItem.equals(newItem);
        }
    };

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
