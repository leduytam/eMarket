package com.group05.emarket.repositories;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.group05.emarket.models.Category;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CategoryRepository {
    private static CategoryRepository instance;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CategoryRepository() {
    }

    public static CategoryRepository getInstance() {
        if (instance == null) {
            instance = new CategoryRepository();
        }

        return instance;
    }

    public CompletableFuture<List<Category>> getCategories() {
        var future = new CompletableFuture<List<Category>>();

        Query query = db.collection("categories").orderBy("createdAt");

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                var categories = task.getResult().toObjects(Category.class);
                future.complete(categories);
            } else {
                future.completeExceptionally(task.getException());
            }
        });

        return future;
    }
}
