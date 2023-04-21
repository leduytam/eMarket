package com.group05.emarket.repositories;

import android.os.Handler;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.group05.emarket.MockData;
import com.group05.emarket.enums.SortProductOption;
import com.group05.emarket.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ProductRepository {
    private static ProductRepository instance;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final DocumentReference productRef = db.collection("products").document();

    public static ProductRepository getInstance() {
        if (instance == null) {
            instance = new ProductRepository();
        }

        return instance;
    }

    public CompletableFuture<Integer> getProductPageCount(String q, UUID categoryId, float[] priceRange, int itemsPerPage) {
        CompletableFuture<Integer> future = new CompletableFuture<>();

//        Query query = db.collection("products")
//                .whereEqualTo("categoryId", categoryId)
//                .whereGreaterThanOrEqualTo("name", q)
//                .whereLessThanOrEqualTo("name", q + "\uf8ff")
//                .whereGreaterThanOrEqualTo("price", priceRange[0])
//                .whereLessThanOrEqualTo("price", priceRange[1]);
//
//        query.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                int count = task.getResult().size();
//                future.complete((int) Math.ceil((double) count / itemsPerPage));
//            } else {
//                future.completeExceptionally(task.getException());
//            }
//        });

        new Handler().postDelayed(() -> {
            future.complete((int) Math.ceil((double) MockData.getProducts().size() / itemsPerPage));
        }, 0);

        return future;
    }

    public CompletableFuture<List<Product>> getProducts(String q, UUID categoryId, float[] priceRange, SortProductOption sortOption, int page, int itemsPerPage) {
        CompletableFuture<List<Product>> future = new CompletableFuture<>();

        new Handler().postDelayed(() -> {
            List<Product> products = MockData.getProducts(q, categoryId, priceRange, sortOption);

            int start = (page - 1) * itemsPerPage;
            int end = Math.min(start + itemsPerPage, products.size());

            future.complete(products.subList(start, end));
        }, new Random().nextInt(300) + 200);

//        Query query = productRef.collection("products")
//                .whereEqualTo("categoryId", categoryId)
//                .whereGreaterThanOrEqualTo("name", q)
//                .whereLessThanOrEqualTo("name", q + "\uf8ff")
//                .whereGreaterThanOrEqualTo("price", priceRange[0])
//                .whereLessThanOrEqualTo("price", priceRange[1]);
//
//        if (sortOption == SortProductOption.NAME_ASCENDING) {
//            query = query.orderBy("name");
//        } else if (sortOption == SortProductOption.NAME_DESCENDING) {
//            query = query.orderBy("name", Query.Direction.DESCENDING);
//        } else if (sortOption == SortProductOption.PRICE_ASCENDING) {
//            query = query.orderBy("price");
//        } else if (sortOption == SortProductOption.PRICE_DESCENDING) {
//            query = query.orderBy("price", Query.Direction.DESCENDING);
//        } else if (sortOption == SortProductOption.HIGHEST_RATED) {
//            query = query.orderBy("avgRating");
//        } else if (sortOption == SortProductOption.LOWEST_RATED) {
//            query = query.orderBy("avgRating", Query.Direction.DESCENDING);
//        }
//
//        query = query.limit(itemsPerPage).startAfter(page * itemsPerPage);
//
//        query.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                var products = new ArrayList<Product>();
//
//                for (DocumentSnapshot document : task.getResult()) {
//                    Product product = document.toObject(Product.class);
//                    products.add(product);
//                }
//
//                future.complete(products);
//            } else {
//                future.completeExceptionally(task.getException());
//            }
//        });

        return future;
    }
}
