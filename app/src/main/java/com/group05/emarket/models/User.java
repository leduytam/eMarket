package com.group05.emarket.models;

import com.google.firebase.firestore.DocumentId;

import java.util.Date;

public class User {

    static public User currentUser;


    @DocumentId
    private String documentId;
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String birthday;


    public User() {
        this.email = "";
        this.password = "";
        this.fullName = "";
        this.phoneNumber = "";
        this.address = "";
        this.birthday = "";
    }

    public User(String email, String password) {
        this.fullName = email;
        this.email = email;
        this.password = password;
        this.phoneNumber = "";
        this.address = "";
        this.birthday = "";
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

}