package com.group05.emarket.repositories;


import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.group05.emarket.models.Product;
import com.group05.emarket.models.User;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group05.emarket.firestore.UsersFirestoreManager;
import com.group05.emarket.schemas.ProductsFirestoreSchema;
import com.group05.emarket.schemas.UsersFirestoreSchema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepository {

    public ProductRepository() {
    }

    public static void createProduct(Product product) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> productMap = new HashMap<>();
        productMap.put(ProductsFirestoreSchema.IMAGE_URL, product.getImageUrl());
        productMap.put(ProductsFirestoreSchema.NAME, product.getName());
        productMap.put(ProductsFirestoreSchema.PRICE, product.getPrice());
        productMap.put(ProductsFirestoreSchema.DISCOUNT, product.getDiscount());
        productMap.put(ProductsFirestoreSchema.DESCRIPTION, product.getDescription());
        productMap.put(ProductsFirestoreSchema.WEIGHT_UNIT, product.getWeightUnit());
        productMap.put(ProductsFirestoreSchema.WEIGHT, product.getWeight());
        productMap.put(ProductsFirestoreSchema.CATEGORY_UUID, product.getCategoryUuid());

        db.collection(ProductsFirestoreSchema.COLLECTION_NAME).add(productMap);

    }

//    public static void saveProducts(List<Product> products) {
//        for (Product product : products) {
//            Log.d("ProductRepository", "saveProducts: " + product.getName());
//            createProduct(product);
//        }
//    }
}
