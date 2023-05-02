package com.group05.emarket.models;

public class Address {
    private String address;
    private String ward;
    private String district;
    private String city;
    private String province;
    private String country;
    private String postalCode;
    private Float latitude;
    private Float longitude;
    private boolean isDefault;

    public Address() {
        this.address = "";
        this.ward = "";
        this.district = "";
        this.city = "";
        this.province = "";
        this.country = "";
        this.postalCode = "";
        this.latitude = 0.0f;
        this.longitude = 0.0f;
        this.isDefault = false;
    }

    public Address(String address, String ward, String district, String city, String province, String country, String postalCode, String latitude, String longitude, boolean isDefault) {
        this.address = address;
        this.ward = ward;
        this.district = district;
        this.city = city;
        this.province = province;
        this.country = country;
        this.postalCode = postalCode;
        this.latitude = Float.parseFloat(latitude);
        this.longitude = Float.parseFloat(longitude);
        this.isDefault = isDefault;
    }

    public String getAddress() {
        return address;
    }

    public void setStreet(String street) {
        this.address = street;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }


    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
}
