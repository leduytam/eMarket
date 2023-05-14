package com.group05.emarket.repositories;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.group05.emarket.models.Address;
import com.group05.emarket.models.CartItem;
import com.group05.emarket.models.DeliveryMan;
import com.group05.emarket.models.Order;
import com.group05.emarket.models.OrderProduct;
import com.group05.emarket.models.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class OrderRepository {
    private static OrderRepository instance;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final AddressRepository addressRepository = AddressRepository.getInstance();

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
        // get user address
        addressRepository.getUserAddress().thenAccept(address -> {
            Log.d("Address", address.getAddress());
            if (address == null) {
                future.complete(new ArrayList<>());
                return;
            }
//            get user info
            db.collection("users").document(user.getUid()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    var userDoc = task.getResult();
                    String name = userDoc.getString("fullName");
                    String phone = userDoc.getString("phoneNumber");
                    String email = userDoc.getString("email");
                    //get all orders have this user reference
                    var userRef = db.collection("users").document(user.getUid());
                    Query query = db.collection("orders").whereEqualTo("userRef", userRef).whereEqualTo("status", status.toString());

                    query.get().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            var orderDocs = task1.getResult().getDocuments();
                            Log.d("Order", String.valueOf(orderDocs.size()));

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
                                Boolean isReviewed = doc.getBoolean("isReviewed");
                                if (isReviewed == null) {
                                    isReviewed = false;
                                }
                                Order order = new Order(id, name, address.getAddress(), phone, email, status, updatedAt, createdAt, totalPrice, isReviewed);
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
                                                    if (order.getOrderProducts().size() == orderItemDocs.size()) {
                                                        orders.add(order);
                                                        if (orders.size() == orderDocs.size()) {
                                                            future.complete(orders);
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            });
        });
        return future;
    }

    public CompletableFuture<Void> placeOrder(List<CartItem> cart, float totalCost, int discount)  {
        CompletableFuture<Void> future = new CompletableFuture<>();

        var user = auth.getCurrentUser();

        if (user == null) {
            future.complete(null);
            return future;
        }
        var userRef = db.collection("users").document(auth.getCurrentUser().getUid());
        db.collection("deliverymen").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                var deliverymenDocs = task.getResult().getDocuments();
                int randomIndex = (int) (Math.random() * deliverymenDocs.size());
                DocumentReference deliverymanRef = deliverymenDocs.get(randomIndex).getReference();
                Map<String, Object> orderData = new HashMap<>();
                orderData.put("userRef", userRef);
                orderData.put("deliverymanRef", deliverymanRef);
                orderData.put("createdAt", new Date());
                orderData.put("updatedAt", new Date());
                orderData.put("status", Order.OrderStatus.PENDING);
                orderData.put("isReviewed", false);
                double totalPrice = 0;
                for (var item : cart) {
                    totalPrice += item.getProduct().getDiscountedPrice() * item.getQuantity();
                }
                orderData.put("totalPrice", totalPrice);
                FirebaseFirestore.getInstance().collection("orders").add(orderData).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        addProducts(cart, task1.getResult().getId());
                        future.complete(null);
                    } else {
                        throw new IllegalStateException(task1.getException());
                    }
                });
            }
        });
        return future;
    }

    public CompletableFuture<String> placeOrder(List<CartItem> cart, Address address) {
        CompletableFuture<String> future = new CompletableFuture<>();

        var user = auth.getCurrentUser();

        if (user == null) {
            future.complete(null);
            return future;
        }
        var userRef = db.collection("users").document(auth.getCurrentUser().getUid());

        Map<String, Object> orderData = new HashMap<>();
        orderData.put("userRef", userRef);
        orderData.put("deliverymanRef", null);
        orderData.put("createdAt", new Date());
        orderData.put("updatedAt", new Date());
        orderData.put("status", Order.OrderStatus.PENDING);
        orderData.put("isReviewed", false);
        double totalPrice = 0;
        for (var item : cart) {
            totalPrice += item.getProduct().getDiscountedPrice() * item.getQuantity();
        }
        orderData.put("totalPrice", totalPrice);
        FirebaseFirestore.getInstance().collection("orders").add(orderData).addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                addProducts(cart, task1.getResult().getId());
                addAddress(address, task1.getResult().getId());
                future.complete(task1.getResult().getId());
            } else {
                throw new IllegalStateException(task1.getException());
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

    private void addAddress(Address address, String orderId) {
        var batch = db.batch();
        var orderRef = db.collection("orders").document(orderId);
        Map<String, Object> data = new HashMap<>();
        data = address.toMap();
        batch.set(orderRef.collection("address").document(), data);
        batch.commit();
    }

    public CompletableFuture<List<OrderProduct>> getOrderProductDetail(String orderId) {
        Query query = db.collection("orders").document(orderId).collection("products");
        CompletableFuture<List<OrderProduct>> future = new CompletableFuture<>();
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                var orderItemDocs = task.getResult().getDocuments();

                if (orderItemDocs.size() == 0) {
                    future.complete(new ArrayList<>());
                    return;
                }

                List<OrderProduct> orderProducts = new ArrayList<>();
                for (var doc1 : orderItemDocs) {
                    DocumentReference productRef = doc1.getDocumentReference("productRef");
                    productRef.get().addOnCompleteListener(productTask -> {
                        if (productTask.isSuccessful()) {
                            Product product = productTask.getResult().toObject(Product.class);
                            int quantity = doc1.getLong("quantity").intValue();
                            orderProducts.add(new OrderProduct(product, quantity, orderId));
                            if (orderProducts.size() == orderItemDocs.size()) {
                                future.complete(orderProducts);
                            }
                        }
                    });
                }
            } else {
                future.completeExceptionally(task.getException());
            }
        });
        return future;
    }

    public CompletableFuture<Void> setOrderStatus(String orderId, Order.OrderStatus status) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        db.collection("orders").document(orderId).update("status", status).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                future.complete(null);
            } else {
                future.completeExceptionally(task.getException());
            }
        });
        return future;
    }

    public CompletableFuture<Void> submitReview(String orderId, MutableLiveData<List<OrderProduct>> products, String review, float rating) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        var userRef = db.collection("users").document(auth.getCurrentUser().getUid());
        var batch = db.batch();
        for (var product : products.getValue()) {
            updateProductRating(product.getProduct().getId());
            var productRef = db.collection("products").document(product.getProduct().getId());
            var reviewRef = db.collection("reviews").document();
            Map<String, Object> data = new HashMap<>();
            data.put("productRef", productRef);
            data.put("review", review);
            data.put("rating", rating);
            data.put("createdAt", new Date());
            data.put("updatedAt", new Date());
            data.put("userRef", userRef);
            batch.set(reviewRef, data);
        }
        db.collection("orders").document(orderId).update("isReviewed", true);
        batch.commit().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                future.complete(null);
            } else {
                future.completeExceptionally(task.getException());
            }
        });
        return future;
    }

    private CompletableFuture<Void> updateProductRating(String productId) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        db.collection("reviews").whereEqualTo("productRef", db.collection("products").document(productId)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                var reviews = task.getResult().getDocuments();
                double totalRating = 0;
                for (var review : reviews) {
                    totalRating += review.getDouble("rating");
                }
                double rating = totalRating / reviews.size();
                db.collection("products").document(productId).update("avgRating", rating);
                db.collection("products").document(productId).update("ratingCount", reviews.size()).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        future.complete(null);
                    } else {
                        future.completeExceptionally(task1.getException());
                    }
                });
            } else {
                future.completeExceptionally(task.getException());
            }
        });
        return future;
    }

    public CompletableFuture<DeliveryMan> getDeliveryman(String orderId) {
        CompletableFuture<DeliveryMan> future = new CompletableFuture<DeliveryMan>();
        db.collection("orders").document(orderId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                var order = new Order();
                order.setId(task.getResult().getId());
                Order.OrderStatus status = Order.OrderStatus.valueOf(task.getResult().getString("status"));
                order.setStatus(status);
                order.setTotalPrice(task.getResult().getDouble("totalPrice"));
                // get deliverymen
                var docDeliverymen = task.getResult().getDocumentReference("deliverymanRef");
                if (docDeliverymen == null) {
                    future.complete(null);
                    return;
                }
                docDeliverymen.get().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        var deliveryman = new DeliveryMan();
                        deliveryman.setId(task1.getResult().getId());
                        deliveryman.setName(task1.getResult().getString("fullName"));
                        deliveryman.setPhone(task1.getResult().getString("phoneNumber"));
                        deliveryman.setEmail(task1.getResult().getString("email"));
                        order.setDeliveryman(deliveryman);
                        future.complete(deliveryman);
                    } else {
                        future.completeExceptionally(task1.getException());
                    }
                });

            } else {
                future.completeExceptionally(task.getException());
            }
        });
        return future;
    }

    public CompletableFuture<Address> getOrderAddress(String orderId) {
        CompletableFuture<Address> future = new CompletableFuture<Address>();
        db.collection("orders").document(orderId).collection("address").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                var addressList = task.getResult().getDocuments();
                if (addressList == null || addressList.size() == 0) {
                    future.complete(null);
                    return;
                }
                var addressDoc = addressList.get(0);
                var address = new Address();
                address.setStreet(addressDoc.getString("address"));
                address.setCity(addressDoc.getString("city"));
                address.setDistrict(addressDoc.getString("district"));
                address.setWard(addressDoc.getString("ward"));
                address.setProvince(addressDoc.getString("province"));
                address.setPostalCode(addressDoc.getString("postalCode"));
                address.setLatitude(addressDoc.getDouble("latitude").floatValue());
                address.setLongitude(addressDoc.getDouble("longitude").floatValue());
                address.setCountry(addressDoc.getString("country"));
                future.complete(address);
            } else {
                future.completeExceptionally(task.getException());
            }
        });
        return future;
    }

    public CompletableFuture<Address> getDeliverymanAddress(String orderId) {
        CompletableFuture<Address> future = new CompletableFuture<Address>();
        db.collection("orders").document(orderId).collection("deliverymanAddress").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                var addressList = task.getResult().getDocuments();
                if (addressList.size() == 0) {
                    future.complete(null);
                    return;
                }
                var addressDoc = addressList.get(0);
                if (addressDoc == null) {
                    future.complete(null);
                    return;
                }
                var address = new Address();
                address.setStreet(addressDoc.getString("address"));
                address.setCity(addressDoc.getString("city"));
                address.setDistrict(addressDoc.getString("district"));
                address.setWard(addressDoc.getString("ward"));
                address.setProvince(addressDoc.getString("province"));
                address.setPostalCode(addressDoc.getString("postalCode"));
                address.setLatitude(addressDoc.getDouble("latitude").floatValue());
                address.setLongitude(addressDoc.getDouble("longitude").floatValue());
                address.setCountry(addressDoc.getString("country"));
                future.complete(address);
            } else {
                future.completeExceptionally(task.getException());
            }
        });
        return future;
    }

}
