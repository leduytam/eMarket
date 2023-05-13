package com.group05.emarket.repositories;

import android.os.Handler;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.group05.emarket.MockData;
import com.group05.emarket.models.Category;
import com.group05.emarket.models.Product;
import com.group05.emarket.models.Review;
import com.group05.emarket.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ReviewRepository {
    private static ReviewRepository instance;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static ReviewRepository getInstance() {
        if (instance == null) {
            instance = new ReviewRepository();
        }

        return instance;
    }

    private ReviewRepository() {
    }

    public CompletableFuture<List<Review>> getReviews(String productId, int limit) {
        CompletableFuture<List<Review>> future = new CompletableFuture<>();

        var productRef = db.collection("products").document(productId);

        Query query = db.collection("reviews")
                .whereEqualTo("productRef", productRef)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(limit);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Task<DocumentSnapshot>> tasks = new ArrayList<>();
                List<Review> reviews = new ArrayList<>();

                for (DocumentSnapshot reviewDoc : task.getResult()) {
                    reviews.add(new Review(reviewDoc.getId(), reviewDoc.getData()));

                    var reviewerRef = reviewDoc.getDocumentReference("userRef");

                    if (reviewerRef != null) {
                        Task<DocumentSnapshot> reviewerTask = reviewerRef.get();
                        tasks.add(reviewerTask);
                    }
                }

                if (tasks.isEmpty()) {
                    future.complete(reviews);
                    return;
                }

                Tasks.whenAllSuccess(tasks).addOnSuccessListener(userDocs -> {
                    int index = 0;

                    for (Object reviewerDoc : userDocs) {
                        var doc = (DocumentSnapshot) reviewerDoc;
                        Review.Reviewer reviewer = new Review.Reviewer(doc.getId(), doc.getData());
                        reviews.get(index).setReviewer(reviewer);
                        index++;
                    }

                    future.complete(reviews);
                }).addOnFailureListener(future::completeExceptionally);

            } else {
                future.completeExceptionally(task.getException());
            }
        });

        return future;
    }
}
