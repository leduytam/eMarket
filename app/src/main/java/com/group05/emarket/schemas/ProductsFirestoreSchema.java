package com.group05.emarket.schemas;

public class ProductsFirestoreSchema {
    // Root collection name
    public static final String COLLECTION_NAME = "products";

    // Document fields
    public static final String IMAGE_URL = "imageUrl";
    public static final String NAME = "name";
    public static final String PRICE = "price";
    public static final String AVG_RATING = "avgRating";
    public static final String RATING_COUNT = "ratingCount";
    public static final String DISCOUNT = "discount";
    public static final String DESCRIPTION = "description";
    public static final String WEIGHT_UNIT = "weightUnit";
    public static final String WEIGHT = "weight";
    public static final String CATEGORY_UUID = "categoryUuid";
}
