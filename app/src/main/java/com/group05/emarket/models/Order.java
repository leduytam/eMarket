package com.group05.emarket.models;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Order {

    public enum OrderStatus {
        PENDING,
        DELIVERING,
        DELIVERED,
        CANCELLED
    }
    private String id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private OrderStatus orderStatus;
    private Date created_at;
    private Date updated_at;
    private double totalPrice;

    private List<OrderProduct> orderProducts;

    private DeliveryMan deliveryMan;
    public Order(String id, String name, String address, String phone, String email, OrderStatus orderStatus, Date created_at, Date updated_at, double totalPrice) {
        orderProducts = new ArrayList<>();
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.orderStatus = orderStatus;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.totalPrice = totalPrice;
    }

    public Order(String id, String name, String address, String phone, String email, OrderStatus orderStatus, Date created_at, Date updated_at, List<OrderProduct> products, DeliveryMan deliveryMan) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.orderStatus = orderStatus;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.orderProducts = products;
        this.deliveryMan = deliveryMan;
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

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() { return phone; }

    public OrderStatus getStatus() {
        return orderStatus;
    }

    public void setStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
    public void addOrderProduct(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
    }
    public List<OrderProduct> getOrderProducts() {
        return this.orderProducts;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderProduct> getProducts() {
        return orderProducts;
    }

    public void setProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public DeliveryMan getDeliveryMan() {
        return deliveryMan;
    }

    public void setDeliveryMan(DeliveryMan deliveryMan) {
        this.deliveryMan = deliveryMan;
    }

}
