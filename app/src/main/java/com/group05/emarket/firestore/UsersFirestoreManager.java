package com.group05.emarket.firestore;

import static com.group05.emarket.schemas.UsersSchema.COLLECTION_NAME;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.group05.emarket.models.User;
import com.group05.emarket.utilities.Encrypt;

public class UsersFirestoreManager {
    private static UsersFirestoreManager usersFirestoreManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference contactsCollectionReference;
    private static FirebaseAuth mAuth;

    public static UsersFirestoreManager newInstance() {
        if (usersFirestoreManager == null) {
            usersFirestoreManager = new UsersFirestoreManager();
        }
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
        return usersFirestoreManager;
    }

    private UsersFirestoreManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        contactsCollectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }

    public boolean createUser(User user) {
        try {
            // validate email is not exist
            getContactByEmail(user.getEmail(), task -> {
                if (task.isSuccessful()) {
                    if (task.getResult().size() == 0) {
                        contactsCollectionReference.add(new User(user.getEmail(), Encrypt.hashPassword(user.getPassword())));
                    } else {
                        Log.d("UserFirestoreManager", "Email is already exist");
                        throw new IllegalArgumentException("Email is already exist");
                    }
                }
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void getAllUsers(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        contactsCollectionReference.get().addOnCompleteListener(onCompleteListener);
    }

    public void updateUser(User user) {
        String documentId = user.getDocumentId();
        DocumentReference documentReference = contactsCollectionReference.document(documentId);
        documentReference.set(user);
    }

    public void deleteUser(String documentId) {
        DocumentReference documentReference = contactsCollectionReference.document(documentId);
        documentReference.delete();
    }

    public void getContactByEmail(String email, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        contactsCollectionReference.whereEqualTo("email", email).get().addOnCompleteListener(onCompleteListener);
    }

    public Task<AuthResult> login(User user) {
        try {
            var taskResult = mAuth.signInWithEmailAndPassword(user.getEmail(), Encrypt.hashPassword(user.getPassword()));
            return taskResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Task<AuthResult>  signUp(User user) {
        try {
            var taskResult = mAuth.createUserWithEmailAndPassword(user.getEmail(), Encrypt.hashPassword(user.getPassword()));
            return taskResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
