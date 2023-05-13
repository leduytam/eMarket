package com.group05.emarket.repositories;

import com.google.firebase.firestore.FirebaseFirestore;
import com.group05.emarket.models.Voucher;

import java.util.concurrent.CompletableFuture;

public class VoucherRepository {
    private static VoucherRepository instance;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static VoucherRepository getInstance() {
        if (instance == null) {
            instance = new VoucherRepository();
        }
        return instance;
    }

    public CompletableFuture<Voucher> getVoucherDiscount(String voucherCode) {
        CompletableFuture<Voucher> future = new CompletableFuture<>();
        db.collection("vouchers").document(voucherCode).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                var voucherDoc = task.getResult();
                if (voucherDoc.exists()) {
                    var discount = voucherDoc.getLong("discount");
                    Voucher voucher = new Voucher(voucherCode, discount.intValue());
                    future.complete(voucher);
                } else {
                    future.complete(null);
                }
            } else {
                future.completeExceptionally(task.getException());
            }
        });
        return future;
    }
}
