package com.group05.emarket.firestore;

public final class ContactsFirestoreDbContract {

    // Root collection name
    public static final String COLLECTION_NAME = "contacts";

    // Document ID
    public static final String DOCUMENT_ID = "document_id";

    // Document field names
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";


    // To prevent someone from accidentally instantiating the contract 		class, make the constructor private
    private ContactsFirestoreDbContract() {}
}