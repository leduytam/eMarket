package com.group05.emarket.repositories;

import com.group05.emarket.MockData;
import com.group05.emarket.models.Product;

import java.util.List;
import java.util.UUID;

public class ProductRepository {
    public List<Product> getProducts() {
        return MockData.getProducts();
    }

    public Product getProductById(UUID id) {
        return MockData.getProductById(id);
    }

    public List<Product> getPopularProducts() {
        return MockData.getProducts().subList(0, 3);
    }

    public List<Product> getNewProducts() {
        return MockData.getProducts().subList(0, 3);
    }

    public List<Product> getDiscountProducts() {
        return MockData.getProducts().subList(0, 3);
    }

    public List<Product> getRelatedProducts(UUID id) {
        return MockData.getProducts().subList(0, 3);
    }

    public List<Product> getProductsByCategory(String category) {
        return MockData.getProducts();
    }
}
