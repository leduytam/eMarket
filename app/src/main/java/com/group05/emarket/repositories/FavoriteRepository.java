package com.group05.emarket.repositories;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group05.emarket.models.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class FavoriteRepository {
    public static FavoriteRepository instance;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    public static FavoriteRepository getInstance() {
        if (instance == null) {
            instance = new FavoriteRepository();
        }
        return instance;
    }

    private FavoriteRepository() {
    }

    public CompletableFuture<List<Product>> getUserFavoriteProducts() {
        CompletableFuture<List<Product>> future = new CompletableFuture<>();

        var user = auth.getCurrentUser();

        if (user == null) {
            future.complete(null);
            return future;
        }

        db.collection("users").document(user.getUid()).collection("favorites").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                var productIdDocs = task.getResult().getDocuments();
                if (productIdDocs.size() == 0) {
                    future.complete(new ArrayList<>());
                    return;
                }

                var productIds = productIdDocs.stream().map(productIdDoc -> productIdDoc.getId()).toArray(String[]::new);
                List<Product> products = new ArrayList<>();
                for (String productId : productIds) {
                    db.collection("products").document(productId).get().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Product product = new Product(productId, task1.getResult().getData());
                            products.add(product);
                            if (products.size() == productIds.length) {
                                future.complete(products);
                            }
                        } else {
                            future.completeExceptionally(task1.getException());
                        }
                    });
                }
            } else {
                future.completeExceptionally(task.getException());
            }
        });
        return future;
    }


    public CompletableFuture<Boolean> addProductToFavorite(String productId) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        var user = auth.getCurrentUser();

        if (user == null) {
            future.complete(false);
            return future;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("productId", productId);

        try {
            db.collection("users").document(user.getUid()).collection("favorites").document(productId).set(data).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    future.complete(true);
                } else {
                    future.completeExceptionally(task.getException());
                }
            });
        } catch (Exception e) {
            future.completeExceptionally(e);
        }
        return future;
    }

    public CompletableFuture<Boolean> removeProductFromFavorite(String productId) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        var user = auth.getCurrentUser();

        if (user == null) {
            future.complete(false);
            return future;
        }

        db.collection("users").document(user.getUid()).collection("favorites").document(productId).delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                future.complete(true);
            } else {
                future.completeExceptionally(task.getException());
            }
        });
        return future;
    }
}
