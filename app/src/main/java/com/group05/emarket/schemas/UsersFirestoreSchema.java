package com.group05.emarket.schemas;

import java.util.Date;

public final class UsersFirestoreSchema {

    // Root collection name
    public static final String COLLECTION_NAME = "users";

    // Document fields
    public static final String DOCUMENT_ID = "document_id";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String FULL_NAME = "fullName";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String ADDRESS = "address";
    public static final String CITY = "city";
    public static final String BIRTHDAY = "birthday";


    private UsersFirestoreSchema() {}
}