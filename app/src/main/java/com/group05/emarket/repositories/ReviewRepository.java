package com.group05.emarket.repositories;

import android.os.Handler;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.group05.emarket.MockData;
import com.group05.emarket.models.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import kotlinx.coroutines.scheduling.Task;

public class ReviewRepository {
    private static ReviewRepository instance;
//    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
//    private final DocumentReference reviewRef = db.collection("reviews").document();

    public static ReviewRepository getInstance() {
        if (instance == null) {
            instance = new ReviewRepository();
        }

        return instance;
    }

    private ReviewRepository() {
    }

    public CompletableFuture<Integer> getReviewPageCount(UUID productId, int itemsPerPage) {
        CompletableFuture<Integer> future = new CompletableFuture<>();

//        Query query = reviewRef
//                .collection("reviews")
//                .whereEqualTo("productId", productId);
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
            future.complete((int) Math.ceil((double) MockData.getReviews().size() / itemsPerPage));
        }, 0);

        return future;
    }

    public CompletableFuture<List<Review>> getReviews(UUID productId, int page, int itemsPerPage) {
        CompletableFuture<List<Review>> future = new CompletableFuture<>();

        new Handler().postDelayed(() -> {
            var start = (page - 1) * itemsPerPage;
            var end = Math.min(page * itemsPerPage, MockData.getReviews().size());
            future.complete(MockData.getReviews().subList(start, end));
        }, 1000);

//        Query query = reviewRef
//                .collection("reviews")
//                .whereEqualTo("productId", productId)
//                .orderBy("createdAt", Query.Direction.DESCENDING)
//                .startAfter((page - 1) * itemsPerPage)
//                .limit(itemsPerPage);
//
//        query.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                for (DocumentSnapshot document : task.getResult()) {
//                    Review review = document.toObject(Review.class);
//                    future.complete(new ArrayList<>() {{
//                        add(review);
//                    }});
//                }
//            } else {
//                future.completeExceptionally(task.getException());
//            }
//        });

        return future;
    }
}
