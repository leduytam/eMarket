package com.group05.emarket.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.group05.emarket.models.Payment;
import com.group05.emarket.repositories.PaymentRepository;

import java.util.List;

public class PaymentViewModel extends ViewModel {
    private final PaymentRepository paymentRepo = PaymentRepository.getInstance();

    public PaymentViewModel() {
    }

    public LiveData<List<Payment>> getPayments() {
        return paymentRepo.getPayments();
    }




}
