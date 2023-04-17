package com.group05.emarket.models;

public class BannerItem {
    private int mImageResId;
    private int mBackground;
    private int mBorderRadius;

    private int mPadding;
    private int mPaddingHorizontal;
    private int mPaddingVertical;

    public BannerItem(int imageResId) {
        mImageResId = imageResId;
        mBackground = 0;
        mBorderRadius = 0;
        mPadding = 0;
        mPaddingHorizontal = 0;
        mPaddingVertical = 0;
    }

    public BannerItem(int imageResId, int background) {
        mImageResId = imageResId;
        mBackground = background;
    }

    public BannerItem(int imageResId, int background, int borderRadius) {
        mImageResId = imageResId;
        mBackground = background;
        mBorderRadius = borderRadius;
    }

    public int getImageResId() {
        return mImageResId;
    }

    public int getBackground() {
        return mBackground;
    }

    public int getBorderRadius() {
        return mBorderRadius;
    }

    public void setBorderRadius(int borderRadius) {
        mBorderRadius = borderRadius;
    }

    public void setBackground(int background) {
        mBackground = background;
    }

    public int getPadding() {
        return mPadding;
    }

    public void setPadding(int padding) {
        mPadding = padding;
    }

    public int getPaddingHorizontal() {
        return mPaddingHorizontal;
    }

    public void setPaddingHorizontal(int paddingHorizontal) {
        mPaddingHorizontal = paddingHorizontal;
    }

    public int getPaddingVertical() {
        return mPaddingVertical;
    }

    public void setPaddingVertical(int paddingVertical) {
        mPaddingVertical = paddingVertical;
    }

}
