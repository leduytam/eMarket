package com.group05.emarket.models;

public class Voucher {
    private String id;
    private int discount;

    public Voucher(String id, int discount) {
        this.id = id;
        this.discount = discount;
    }

    public String getId() {
        return id;
    }

    public int getDiscount() {
        return discount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
