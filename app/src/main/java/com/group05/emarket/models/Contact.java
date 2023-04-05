package com.group05.emarket.models;

import com.google.firebase.firestore.DocumentId;

public class Contact {
    @DocumentId
    private String documentId;
    private String email;
    private String password;

    public Contact() {
        this.email = "";
        this.password = "";
    }


    public Contact(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}