package com.group05.emarket.schemas;

public final class UsersFirestoreSchema {

    // Root collection name
    public static final String COLLECTION_NAME = "users";

    // Document fields
    public static final String DOCUMENT_ID = "document_id";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    private UsersFirestoreSchema() {}
}