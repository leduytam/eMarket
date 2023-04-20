package com.group05.emarket.firestore;

import com.google.android.gms.tasks.Task;

import static com.group05.emarket.schemas.ProductsFirestoreSchema.COLLECTION_NAME;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.group05.emarket.models.Product;

public class ProductsFirestoreManger {
    private static ProductsFirestoreManger productsFirestoreManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference productsCollectionReference;

    public static ProductsFirestoreManger newInstance() {
        if (productsFirestoreManager == null) {
            productsFirestoreManager = new ProductsFirestoreManger();
        }
        return productsFirestoreManager;
    }

    private ProductsFirestoreManger() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        productsCollectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }

    public Task<DocumentSnapshot> getProductById(String id) {
        return productsCollectionReference.document(id).get();
    }
}
