package com.group05.emarket.repositories;

import static com.group05.emarket.schemas.UsersFirestoreSchema.COLLECTION_NAME;
import static com.group05.emarket.schemas.UsersFirestoreSchema.PHONE_NUMBER;
import static com.group05.emarket.schemas.UsersFirestoreSchema.FULL_NAME;
import static com.group05.emarket.schemas.UsersFirestoreSchema.ADDRESS;
import static com.group05.emarket.schemas.UsersFirestoreSchema.EMAIL;
import static com.group05.emarket.schemas.UsersFirestoreSchema.BIRTHDAY;

import com.group05.emarket.models.User;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    public UserRepository() {
    }

    public static void updateUser(User user, String uuid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection(COLLECTION_NAME).document(uuid);
        Map<String, Object> userMap = new HashMap<>();
        userMap.put(EMAIL, user.getEmail());
        userMap.put(PHONE_NUMBER, user.getPhoneNumber());
        userMap.put(FULL_NAME, user.getFullName());
        userMap.put(ADDRESS, user.getAddress());
        userMap.put(BIRTHDAY, user.getBirthday());

        userRef.update(userMap);
    }

    public static void createUser(User user, String uuid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection(COLLECTION_NAME).document(uuid);
        Map<String, Object> userMap = new HashMap<>();
        userMap.put(EMAIL, user.getEmail());
        userMap.put(PHONE_NUMBER, user.getPhoneNumber());
        userMap.put(FULL_NAME, user.getFullName());
        userMap.put(ADDRESS, user.getAddress());
        userMap.put(BIRTHDAY, user.getBirthday());

        userRef.set(userMap);

    }

    public static User getCurrentUserDetail() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userUid = firebaseUser.getUid();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection(COLLECTION_NAME).document(userUid);
        if (documentReference != null) {
            User currentUser = new User();
            currentUser.setDocumentId(userUid);
            currentUser.setEmail(firebaseUser.getEmail());
            documentReference.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    currentUser.setFullName(task.getResult().getString(FULL_NAME));
                    currentUser.setPhoneNumber(task.getResult().getString(PHONE_NUMBER));
                    currentUser.setAddress(task.getResult().getString(ADDRESS));
                    currentUser.setBirthday(task.getResult().getString(BIRTHDAY));
                }
            });
            return currentUser;

        }
        return null;
    }
}
