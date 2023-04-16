package com.group05.emarket.models;

public class Order {

    enum Status {
        PENDING,
        DELIVERING,
        DELIVERED,
        CANCELLED
    }
    private int id;
    private float totalPrice;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String note;
    private Status status;
    private String created_at;
    private String updated_at;

    private Product[] products;

    private DeliveryMan deliveryMan;

    public Order(int id, String name, String address, String phone, String email, String note, Status status, String created_at, String updated_at, float totalPrice, Product[] products, DeliveryMan deliveryMan) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.note = note;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.totalPrice = totalPrice;
        this.products = products;
        this.deliveryMan = deliveryMan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }

    public DeliveryMan getDeliveryMan() {
        return deliveryMan;
    }

    public void setDeliveryMan(DeliveryMan deliveryMan) {
        this.deliveryMan = deliveryMan;
    }

    public static class Builder {
        private int id;
        private String name;
        private String address;
        private String phone;
        private String email;
        private String note;
        private Status status;
        private String created_at;
        private String updated_at;
        private float totalPrice;
        private Product[] products;
        private DeliveryMan deliveryMan;

        public Builder() {
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder note(String note) {
            this.note = note;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Builder created_at(String created_at) {
            this.created_at = created_at;
            return this;
        }

        public Builder updated_at(String updated_at) {
            this.updated_at = updated_at;
            return this;
        }

        public Builder totalPrice(float totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Builder products(Product[] products) {
            this.products = products;
            return this;
        }

        public Builder deliveryMan(DeliveryMan deliveryMan) {
            this.deliveryMan = deliveryMan;
            return this;
        }

        public Order build() {
            var order = new Order(id, name, address, phone, email, note, status, created_at, updated_at, totalPrice, products, deliveryMan);
            return order;
        }
    }
}
