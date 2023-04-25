package com.group05.emarket.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

public class Category implements Parcelable {
    @DocumentId
    private String id;
    private String name;
    private String image;
    private String backgroundColor;
    private Date createdAt;
    private Date updatedAt;

    public Category() {
    }

    public Category(String id, Map<String, Object> data) {
        this.id = id;
        this.name = (String) data.get("name");
        this.image = (String) data.get("image");
        this.backgroundColor = (String) data.get("backgroundColor");
        this.createdAt = ((Timestamp) data.get("createdAt")).toDate();
        this.updatedAt = ((Timestamp) data.get("updatedAt")).toDate();
    }

    protected Category(Parcel in) {
        id = in.readString();
        name = in.readString();
        image = in.readString();
        backgroundColor = in.readString();
        createdAt = (Date) in.readSerializable();
        updatedAt = (Date) in.readSerializable();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(backgroundColor);
        dest.writeSerializable(createdAt);
        dest.writeSerializable(updatedAt);
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
