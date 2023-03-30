package com.group05.emarket.models;

public class Category {
    private String _name;
    private int _image;

    public Category(String name, int image) {
        this._name = name;
        this._image = image;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public int getImage() {
        return _image;
    }

    public void setImage(int image) {
        this._image = image;
    }
}
