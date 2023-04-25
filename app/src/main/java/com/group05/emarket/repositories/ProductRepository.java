package com.group05.emarket.repositories;

import android.os.Handler;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.group05.emarket.MockData;
import com.group05.emarket.enums.ESortProductOption;
import com.group05.emarket.models.Category;
import com.group05.emarket.models.Product;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ProductRepository {
    private static ProductRepository instance;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static ProductRepository getInstance() {
        if (instance == null) {
            instance = new ProductRepository();
        }

        return instance;
    }

    public CompletableFuture<List<Product>> getProducts(String categoryId) {
        Query query = db.collection("products");

        if (categoryId != null) {
            var categoryRef = db.collection("categories").document(categoryId);
            query = query.whereEqualTo("categoryRef", categoryRef);
        }

        return getProductsByQuery(query);
    }

    public CompletableFuture<List<Product>> getProducts(String categoryId, int limit) {
        Query query = db.collection("products");

        if (categoryId != null) {
            var categoryRef = db.collection("categories").document(categoryId);
            query = query.whereEqualTo("categoryRef", categoryRef);
        }

        query = query.limit(limit);

        return getProductsByQuery(query);
    }

    public CompletableFuture<List<Product>> getPopularProducts(int limit) {

        Query query = db.collection("products")
                .orderBy("sold", Query.Direction.DESCENDING)
                .limit(limit);

        return getProductsByQuery(query);
    }

    public CompletableFuture<List<Product>> getNewProducts(int limit) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date lastMonth = cal.getTime();

        Query query = db.collection("products")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .whereGreaterThanOrEqualTo("createdAt", lastMonth)
                .limit(limit);

        return getProductsByQuery(query);
    }

    public CompletableFuture<List<Product>> getDiscountProducts(int limit) {
        Query query = db.collection("products")
                .whereGreaterThan("discount", 0)
                .orderBy("discount", Query.Direction.DESCENDING)
                .limit(limit);

        return getProductsByQuery(query);
    }

    private CompletableFuture<List<Product>> getProductsByQuery(Query query) {
        CompletableFuture<List<Product>> future = new CompletableFuture<>();

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Task<DocumentSnapshot>> tasks = new ArrayList<>();
                List<Product> products = new ArrayList<>();

                for (DocumentSnapshot productDoc : task.getResult()) {
                    Product product = new Product(productDoc.getId(), productDoc.getData());
                    products.add(product);

                    var categoryRef = productDoc.getDocumentReference("categoryRef");

                    if (categoryRef != null) {
                        Task<DocumentSnapshot> categoryTask = categoryRef.get();
                        tasks.add(categoryTask);
                    }
                }

                if (tasks.isEmpty()) {
                    future.complete(products);
                    return;
                }

                Tasks.whenAllSuccess(tasks).addOnSuccessListener(categoryDocs -> {
                    int index = 0;
                    for (Object categoryDoc : categoryDocs) {
                        Category category = ((DocumentSnapshot) categoryDoc).toObject(Category.class);
                        products.get(index).setCategory(category);
                        index++;
                    }
                    future.complete(products);
                }).addOnFailureListener(future::completeExceptionally);

            } else {
                future.completeExceptionally(task.getException());
            }
        });

        return future;
    }
}
