package com.group05.emarket.repositories;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.group05.emarket.models.Address;
import com.group05.emarket.schemas.UsersSchema;

import java.util.concurrent.CompletableFuture;

public class AddressRepository {
    private static AddressRepository instance;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    public static AddressRepository getInstance() {
        if (instance == null) {
            instance = new AddressRepository();
        }
        return instance;
    }

    private AddressRepository() {
    }

    public CompletableFuture<Address> getUserAddress() {
        CompletableFuture<Address> future = new CompletableFuture<>();

        var user = auth.getCurrentUser();

        if (user == null) {
            future.complete(null);
            return future;
        }

        Query query = db.collection(UsersSchema.COLLECTION_NAME).document(user.getUid()).collection(UsersSchema.ADDRESSES);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                var addressDocs = task.getResult().getDocuments();

                if (addressDocs.size() == 0) {
                    future.complete(null);
                    return;
                }
                // get the default address
                var defaultAddress = addressDocs.stream().filter(addressDoc -> {
                    var address = addressDoc.toObject(Address.class);
                    return address.getIsDefault();
                }).findFirst();
                var addressDoc = addressDocs.get(0);
                if (defaultAddress.isPresent()) {
                    addressDoc = defaultAddress.get();
                }
                var address = addressDoc.toObject(Address.class);
                future.complete(address);
            } else {
                future.completeExceptionally(task.getException());
            }
        });

        return future;
    }
}
