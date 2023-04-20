package com.group05.emarket.models;

public class Payment {

    private String id;

    private String title;

    private String cardNumber;

    private int image;

    private boolean isPrimary;

    private Payment() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getImage() {
        return image;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public static class Builder {
        private String id;

        private String title;

        private String cardNumber;

        private int image;

        private boolean isPrimary;


        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public Builder setImage(int image) {
            this.image = image;
            return this;
        }

        public Builder setPrimary(boolean isPrimary) {
            this.isPrimary = isPrimary;
            return this;
        }

        public Payment build() {
            var payment = new Payment();
            payment.id = id;
            payment.title = title;
            payment.cardNumber = cardNumber;
            payment.image = image;
            payment.isPrimary = isPrimary;
            return payment;
        }
    }
}
