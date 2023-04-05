package com.group05.emarket.firestore;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.group05.emarket.models.Contact;

public class ContactsFirestoreManager {
    private static ContactsFirestoreManager contactsFirestoreManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference contactsCollectionReference;

    public static ContactsFirestoreManager newInstance() {
        if (contactsFirestoreManager == null) {
            contactsFirestoreManager = new ContactsFirestoreManager();
        }
        return contactsFirestoreManager;
    }

    private ContactsFirestoreManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        contactsCollectionReference = firebaseFirestore.collection("contacts");
    }

    public void createDocument(Contact contact) {
        contactsCollectionReference.add(contact);
    }

    public void getAllContacts(OnCompleteListener<QuerySnapshot> onCompleteListener)
    {
        contactsCollectionReference.get().addOnCompleteListener(onCompleteListener);
    }

    public void updateContact(Contact contact) {
        String documentId = contact.getDocumentId();
        DocumentReference documentReference = contactsCollectionReference.document(documentId);
        documentReference.set(contact);
    }

    public void deleteContact(String documentId) {
        DocumentReference documentReference = contactsCollectionReference.document(documentId);
        documentReference.delete();
    }
}
