package com.group05.emarket.repositories;


import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group05.emarket.models.CartItem;
import com.group05.emarket.models.Order;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class OrderRepository {
    private static OrderRepository instance;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    public static OrderRepository getInstance() {
        if (instance == null) {
            instance = new OrderRepository();
        }
        return instance;
    }

    private OrderRepository() {
    }

    public CompletableFuture<List<Order>> getOrders() {
        CompletableFuture<List<Order>> future = new CompletableFuture<>();
//
//        var user = auth.getCurrentUser();
//
//        if (user == null) {
//            future.complete(new ArrayList<>());
//            return future;
//        }
//
//        Query query = db.collection("users").document(user.getUid()).collection("order");
//
//        query.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                var orderItemDocs = task.getResult().getDocuments();
//
//                if (orderItemDocs.size() == 0) {
//                    future.complete(new ArrayList<>());
//                    return;
//                }
//
//                List<CartItem> cart = new ArrayList<>();
//
//                for (var doc : orderItemDocs) {
//                    DocumentReference productRef = doc.getDocumentReference("productRef");
//                    int quantity = doc.getLong("quantity").intValue();
//
//                    productRef.get().addOnCompleteListener(productTask -> {
//                        if (productTask.isSuccessful()) {
//                            Product product = productTask.getResult().toObject(Product.class);
//                            cart.add(new CartItem(product, quantity));
//
//                            if (cart.size() == orderItemDocs.size()) {
//                                future.complete(cart);
//                            }
//                        } else {
//                            future.completeExceptionally(productTask.getException());
//                        }
//                    });
//                }
//            } else {
//                future.completeExceptionally(task.getException());
//            }
//        });

        return future;
    }

    public CompletableFuture<Void> placeOrder(List<CartItem> cart) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = new CompletableFuture<>();

        var user = auth.getCurrentUser();

        if (user == null) {
            future.complete(null);
            return future;
        }
        var userRef = db.collection("users").document(auth.getCurrentUser().getUid());
        Map<String, Object> orderData = new HashMap<>();
        orderData.put("userRef", userRef);
        orderData.put("createdAt", LocalDateTime.now());
        orderData.put("updatedAt", LocalDateTime.now());
        orderData.put("status", Order.OrderStatus.PENDING);
        FirebaseFirestore.getInstance().collection("orders").add(orderData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                addProducts(cart, task.getResult().getId());
                future.complete(null);
            } else {
                throw new IllegalStateException(task.getException());
            }
        });
        return future;
    }

    private void addProducts(List<CartItem> cart, String orderId) {
        Log.d("OrderRepository", "placeOrder: " + orderId);
        var batch = db.batch();
        var orderRef = db.collection("orders").document(orderId).collection("products");
        for (var item : cart) {
            Map<String, Object> data = new HashMap<>();
            DocumentReference productRef = db.collection("products").document(item.getProduct().getId());

            data.put("productRef", productRef);
            data.put("quantity", item.getQuantity());

            batch.set(orderRef.document(), data);
            Log.d("OrderRepository", "placeOrder2: " + item.getProduct().getId());
        }
        batch.commit();
    }
}
