package com.group05.emarket.repositories;



import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.group05.emarket.models.CartItem;
import com.group05.emarket.models.Order;
import com.group05.emarket.models.OrderProduct;
import com.group05.emarket.models.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

    public CompletableFuture<List<Order>> getOrders(Order.OrderStatus status) {
        CompletableFuture<List<Order>> future = new CompletableFuture<>();

        var user = auth.getCurrentUser();

        if (user == null) {
            future.complete(new ArrayList<>());
            return future;
        }
        db.collection("users").document(user.getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                var userDoc = task.getResult();
                String name = userDoc.getString("name");
                String phone = userDoc.getString("phone");
                String address = userDoc.getString("address");
                String email = userDoc.getString("email");
                //get all orders have this user reference
                var userRef = db.collection("users").document(user.getUid());
                Query query = db.collection("orders").whereEqualTo("userRef", userRef).whereEqualTo("status", status.toString());

                query.get().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        var orderDocs = task1.getResult().getDocuments();

                        if (orderDocs.size() == 0) {
                            future.complete(new ArrayList<>());
                            return;
                        }

                        List<Order> orders = new ArrayList<>();
                        for (var doc : orderDocs) {
                            String id = doc.getId();
                            Date updatedAt = doc.getDate("updatedAt");
                            Date createdAt = doc.getDate("createdAt");
                            double totalPrice = doc.getDouble("totalPrice");
                            Order.OrderStatus currentStatus = Order.OrderStatus.valueOf(doc.getString("status"));
                            Order order = new Order(id, name, address, phone, email, currentStatus, updatedAt, createdAt, totalPrice);
                            //get all cart items of this order
                            Query query1 = db.collection("orders").document(id).collection("products");
                            query1.get().addOnCompleteListener(task2 -> {
                                if (task2.isSuccessful()) {
                                    var orderItemDocs = task2.getResult().getDocuments();

                                    if (orderItemDocs.size() == 0) {
                                        future.complete(new ArrayList<>());
                                        return;
                                    }

                                    for (var doc1 : orderItemDocs) {
                                        DocumentReference productRef = doc1.getDocumentReference("productRef");
                                        int quantity = doc1.getLong("quantity").intValue();

                                        productRef.get().addOnCompleteListener(productTask -> {
                                            if (productTask.isSuccessful()) {
                                                Product product = productTask.getResult().toObject(Product.class);
                                                order.addOrderProduct(new OrderProduct(product, quantity, id));
                                            }
                                        });
                                    }
                                }
                            });
                            orders.add(order);
                        }
                        future.complete(orders);
                    }
                });
            }
        });

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
        orderData.put("createdAt", new Date());
        orderData.put("updatedAt", new Date());
        orderData.put("status", Order.OrderStatus.PENDING);
        //calculate total price
        double totalPrice = 0;
        for (var item : cart) {
            totalPrice += item.getProduct().getPrice() * item.getQuantity();
        }
        orderData.put("totalPrice", totalPrice);
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
        var batch = db.batch();
        var orderRef = db.collection("orders").document(orderId).collection("products");
        for (var item : cart) {
            Map<String, Object> data = new HashMap<>();
            DocumentReference productRef = db.collection("products").document(item.getProduct().getId());

            data.put("productRef", productRef);
            data.put("quantity", item.getQuantity());

            batch.set(orderRef.document(), data);
        }
        batch.commit();
    }
}