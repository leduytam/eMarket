package com.group05.emarket.repositories;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.group05.emarket.models.Category;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CategoryRepository {
    private static CategoryRepository instance;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final DocumentReference categoryRef = db.collection("categories").document();

    private CategoryRepository() {
    }

    public static CategoryRepository getInstance() {
        if (instance == null) {
            instance = new CategoryRepository();
        }

        return instance;
    }

    public CompletableFuture<List<Category>> getCategories() {
        CompletableFuture<List<Category>> future = new CompletableFuture<>();

        Query query = categoryRef.collection("categories").orderBy("createdAt");

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Category> categories = task.getResult().toObjects(Category.class);
                future.complete(categories);
            } else {
                future.completeExceptionally(task.getException());
            }
        });

        return future;
    }
}
