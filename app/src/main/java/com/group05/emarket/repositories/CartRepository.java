package com.group05.emarket.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.group05.emarket.MockData;
import com.group05.emarket.models.CartItem;
import com.group05.emarket.models.Category;
import com.group05.emarket.models.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CartRepository {
    private static CartRepository instance;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    public static CartRepository getInstance() {
        if (instance == null) {
            instance = new CartRepository();
        }
        return instance;
    }

    private CartRepository() {
    }

    public CompletableFuture<List<CartItem>> getCart() {
        CompletableFuture<List<CartItem>> future = new CompletableFuture<>();

        var user = auth.getCurrentUser();

        if (user == null) {
            future.complete(new ArrayList<>());
            return future;
        }

        Query query = db.collection("users").document(user.getUid()).collection("cart");

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                var cartItemDocs = task.getResult().getDocuments();

                if (cartItemDocs.size() == 0) {
                    future.complete(new ArrayList<>());
                    return;
                }

                List<CartItem> cart = new ArrayList<>();

                for (var doc : cartItemDocs) {
                    DocumentReference productRef = doc.getDocumentReference("productRef");
                    Long quantityField = doc.getLong("quantity");
                    int quantity = quantityField == null ? 0 : quantityField.intValue();

                    productRef.get().addOnCompleteListener(productTask -> {
                        if (productTask.isSuccessful()) {
                            DocumentSnapshot productDoc = productTask.getResult();
                            Product product = productDoc.toObject(Product.class);

                            DocumentReference categoryRef = productDoc.getDocumentReference("categoryRef");
                            categoryRef.get().addOnCompleteListener(categoryTask -> {
                                if (categoryTask.isSuccessful()) {
                                    DocumentSnapshot categoryDoc = categoryTask.getResult();
                                    Category category = categoryDoc.toObject(Category.class);

                                    product.setCategory(category);

                                    cart.add(new CartItem(product, quantity));

                                    if (cart.size() == cartItemDocs.size()) {
                                        future.complete(cart);
                                    }
                                } else {
                                    future.completeExceptionally(categoryTask.getException());
                                }
                            });
                        } else {
                            future.completeExceptionally(productTask.getException());
                        }
                    });
                }
            } else {
                future.completeExceptionally(task.getException());
            }
        });

        return future;
    }

    public CompletableFuture<Void> updateCart(List<CartItem> cart) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        var user = auth.getCurrentUser();

        if (user == null) {
            future.complete(null);
            return future;
        }

        var batch = db.batch();

        var cartRef = db.collection("users").document(user.getUid()).collection("cart");

        cartRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (var doc : task.getResult()) {
                    batch.delete(doc.getReference());
                }

                for (var item : cart) {
                    Map<String, Object> data = new HashMap<>();
                    DocumentReference productRef = db.collection("products").document(item.getProduct().getId());

                    data.put("productRef", productRef);
                    data.put("quantity", item.getQuantity());

                    batch.set(cartRef.document(), data);
                }

                batch.commit().addOnCompleteListener(task1 -> {
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
}
