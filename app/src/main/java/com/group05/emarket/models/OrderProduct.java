package com.group05.emarket.models;

public class OrderProduct extends CartItem {
    private int orderId;

    public OrderProduct(Product product, int quantity, int orderId) {
        super(product, quantity);
        this.orderId = orderId;
    }

    public OrderProduct(CartItem cartItem, int orderId) {
        super(cartItem.getProduct(), cartItem.getQuantity());
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
