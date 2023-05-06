package com.group05.emarket.models;

import static com.group05.emarket.schemas.AddressSchema.*;

import java.util.HashMap;
import java.util.Map;

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

    public Address(String address, String ward, String district, String city, String province, String country, String postalCode, float latitude, float longitude, boolean isDefault) {
        if (address != null) {
            this.address = address;
        } else {
            this.address = "";
        }

        if (ward != null) {
            this.ward = ward;
        } else {
            this.ward = "";
        }

        if (district != null) {
            this.district = district;
        } else {
            this.district = "";
        }

        if (city != null) {
            this.city = city;
        } else {
            this.city = "";
        }

        if (province != null) {
            this.province = province;
        } else {
            this.province = "";
        }

        if (country != null) {
            this.country = country;
        } else {
            this.country = "";
        }

        if (postalCode != null) {
            this.postalCode = postalCode;
        } else {
            this.postalCode = "";
        }

        if (latitude != 0.0) {
            this.latitude = latitude;
        } else {
            this.latitude = 0.0f;
        }

        if (longitude != 0.0) {
            this.longitude = longitude;
        } else {
            this.longitude = 0.0f;
        }

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

    public Map<String, Object> toMap() {
        Map<String, Object> addressMap = new HashMap<>();
        addressMap.put(ADDRESS, address);
        addressMap.put(WARD, ward);
        addressMap.put(DISTRICT, district);
        addressMap.put(CITY, city);
        addressMap.put(PROVINCE, province);
        addressMap.put(COUNTRY, country);
        addressMap.put(POSTAL_CODE, postalCode);
        addressMap.put(LATITUDE, latitude);
        addressMap.put(LONGITUDE, longitude);
        addressMap.put(IS_DEFAULT, isDefault);
        return addressMap;
    }

    public Address(android.location.Address address, boolean isDefault) {
        if ((address.getAddressLine(0)) != null) {
            this.address = (address.getAddressLine(0));
        }
        else {
            this.address = "";
        }
        if (address.getSubLocality() != null) {
            this.setWard(address.getSubLocality());
        }
        else {
            this.setWard("");
        }
        if (address.getSubAdminArea() != null) {
            this.setDistrict(address.getSubAdminArea());
        }
        else {
            this.setDistrict("");
        }
        if (address.getAdminArea() != null) {
            this.setCity(address.getAdminArea());
        }
        else {
            this.setCity("");
        }
        if (address.getSubAdminArea() != null) {
            this.setProvince(address.getSubAdminArea());
        }
        else {
            this.setProvince("");
        }
        if (address.getCountryName() != null) {
            this.setCountry(address.getCountryName());
        }
        else {
            this.setCountry("");
        }
        if (address.getPostalCode() != null) {
            this.setPostalCode(address.getPostalCode());
        }
        else {
            this.setPostalCode("");
        }
        if (address.getLatitude() != 0.0) {
            this.setLatitude((float) address.getLatitude());
        }
        else {
            this.setLatitude(0.0f);
        }
        if (address.getLongitude() != 0.0) {
            this.setLongitude((float) address.getLongitude());
        }
        else {
            this.setLongitude(0.0f);
        }
        this.setIsDefault(isDefault);
    }
}

