package com.group05.emarket.views.dialogs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.group05.emarket.R;
import com.group05.emarket.repositories.AddressRepository;
import com.group05.emarket.viewmodels.AddressViewModel;
import com.group05.emarket.viewmodels.CartViewModel;
import com.group05.emarket.views.activities.LayoutActivity;
import com.group05.emarket.views.activities.MapActivity;
import com.group05.emarket.views.activities.OrderSuccessActivity;

import java.util.concurrent.ExecutionException;

public class CheckoutBottomSheetDialog extends BottomSheetDialog {
    private float totalCost = 0;
    private TextView tvTotalCost;
    private TextView tvUserAddress;
    private Button btnChangeAddress;
    private final CartViewModel cartViewModel;

    private final AddressViewModel addressViewModel;

    public CheckoutBottomSheetDialog(Context context, CartViewModel cartViewModel, AddressViewModel addressViewModel) {
        super(context);
        this.cartViewModel = cartViewModel;
        this.addressViewModel = addressViewModel;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
        updateTotalCost();
    }

    private void updateTotalCost() {
        if (tvTotalCost != null) {
            tvTotalCost.setText(String.format("$%.2f", totalCost));
        }
    }

    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        var view = getLayoutInflater().inflate(R.layout.bottom_sheet_checkout, null);
        this.setContentView(view);
        this.setCanceledOnTouchOutside(true);
        this.setCancelable(true);
        this.setOnShowListener(dialog -> {
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((android.view.View) this.findViewById(com.google.android.material.R.id.design_bottom_sheet));
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        var btnClose = view.findViewById(R.id.btn_close_bottom_sheet);

        tvTotalCost = view.findViewById(R.id.tv_total_cost);
        tvUserAddress = view.findViewById(R.id.tv_user_address);
        btnChangeAddress = view.findViewById(R.id.btn_change_address);

        tvTotalCost.setText(String.format("$%.2f", totalCost));

        Button btnGoToPayment = view.findViewById(R.id.btn_change_payment_method);
        btnGoToPayment.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), LayoutActivity.class);
            intent.putExtra("fragment", "profile");
            getContext().startActivity(intent);
            dismiss();
        });


        btnClose.setOnClickListener(v -> dismiss());

        Button btnConfirm = view.findViewById(R.id.btn_confirm_checkout);
        btnConfirm.setOnClickListener(v -> {
            try {
                cartViewModel.placeOrder();
                Intent intent = new Intent(getContext(), OrderSuccessActivity.class);
                getContext().startActivity(intent);
                dismiss();
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        addressViewModel.getUserAddress().observe(this, address -> {
            if (address != null) {
                tvUserAddress.setText(address.getAddress());
            }
        });

        btnChangeAddress.setOnClickListener(v -> {
            dismiss();
            Intent intent = new Intent(getContext(), MapActivity.class);
            intent.putExtra("isFromCheckout", true);
            getContext().startActivity(intent);
        });

    }
}
