package com.group05.emarket.models;

public class OrderProduct extends CartItem {
    private String orderId;

    public OrderProduct(Product product, int quantity, String orderId) {
        super(product, quantity);
        this.orderId = orderId;
    }

    public OrderProduct(CartItem cartItem, String orderId) {
        super(cartItem.getProduct(), cartItem.getQuantity());
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
