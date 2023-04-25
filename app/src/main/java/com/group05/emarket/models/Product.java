package com.group05.emarket.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;

import java.util.Date;
import java.util.Map;

public class Product implements Parcelable {
    @DocumentId
    private String id;
    private String name;
    private String image;
    private float price;
    private float avgRating;
    private int ratingCount;
    private int discount;
    private String description;
    private float weight;
    private String weightUnit;
    private Category category;
    private Date createdAt;
    private Date updatedAt;

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

    public float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public float getDiscountedPrice() {
        return price - (price * discount / 100);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public Product() {
    }

    public Product(String id, Map<String, Object> map) {
        this.id = id;
        name = (String) map.get("name");
        image = (String) map.get("image");
        price = ((Number) map.get("price")).floatValue();
        avgRating = ((Number) map.get("avgRating")).floatValue();
        ratingCount = ((Number) map.get("ratingCount")).intValue();
        discount = ((Number) map.get("discount")).intValue();
        description = (String) map.get("description");
        weightUnit = (String) map.get("weightUnit");
        weight = ((Number) map.get("weight")).floatValue();
        createdAt = ((Timestamp) map.get("createdAt")).toDate();
        updatedAt = ((Timestamp) map.get("updatedAt")).toDate();
    }

    protected Product(Parcel in) {
        id = in.readString();
        name = in.readString();
        image = in.readString();
        price = in.readFloat();
        avgRating = in.readFloat();
        ratingCount = in.readInt();
        discount = in.readInt();
        description = in.readString();
        weightUnit = in.readString();
        weight = in.readFloat();
        category = in.readParcelable(Category.class.getClassLoader());
        createdAt = (Date) in.readSerializable();
        updatedAt = (Date) in.readSerializable();
    }

    public static final Creator<Product> CREATOR = new Creator<>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeFloat(price);
        dest.writeFloat(avgRating);
        dest.writeInt(ratingCount);
        dest.writeInt(discount);
        dest.writeString(description);
        dest.writeString(weightUnit);
        dest.writeFloat(weight);
        dest.writeParcelable(category, flags);
        dest.writeSerializable(createdAt);
        dest.writeSerializable(updatedAt);
    }
}
