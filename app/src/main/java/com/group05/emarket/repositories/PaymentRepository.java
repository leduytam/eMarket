package com.group05.emarket.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.group05.emarket.models.Payment;

import java.util.List;

public class PaymentRepository {
    private final MutableLiveData<List<Payment>> mutablePayments;
    private static PaymentRepository instance;

    public static PaymentRepository getInstance() {
        if (instance == null) {
            instance = new PaymentRepository();
        }
        return instance;
    }

    private PaymentRepository() {
        mutablePayments = new MutableLiveData<>();
    }

    public void addPayment(Payment payment) {
        mutablePayments.getValue().add(payment);
    }

    public void removePayment(Payment payment) {
        mutablePayments.getValue().remove(payment);
    }

    public void clearPayments() {
        mutablePayments.getValue().clear();
    }

    public LiveData<List<Payment>> getPayments() {
        return mutablePayments;
    }


}
