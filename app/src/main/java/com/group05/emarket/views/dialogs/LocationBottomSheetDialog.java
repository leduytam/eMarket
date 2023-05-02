package com.group05.emarket.views.dialogs;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.group05.emarket.R;
import com.group05.emarket.databinding.BottomSheetLocationBinding;
import com.group05.emarket.repositories.UserRepository;


public class LocationBottomSheetDialog extends BottomSheetDialog {

    private Address address;
    private BottomSheetLocationBinding binding;

    public LocationBottomSheetDialog(Context context) {
        super(context);
    }

    public void setAddress(Address address) {
        this.address = address;
        if (address != null) {
            updateAddress();
        }
    }

    private void updateAddress() {
        if (binding != null) {
            binding.tvAddress.setText(address.getAddressLine(0));
            binding.cbDefaultAddress.setChecked(false);
        }
    }


    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = BottomSheetLocationBinding.bind(getLayoutInflater().inflate(R.layout.bottom_sheet_location, null));
        this.setContentView(binding.getRoot());
        this.setCanceledOnTouchOutside(true);
        this.setCancelable(true);
        this.setOnShowListener(dialog -> {
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((android.view.View) this.findViewById(com.google.android.material.R.id.design_bottom_sheet));
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });
        binding.btnCloseBottomSheet.setOnClickListener(v -> dismiss());
        if (address != null) {
            binding.tvAddress.setText(address.getAddressLine(0));
        }

        binding.btnConfirmLocation.setOnClickListener(v -> {
            boolean isDefault = binding.cbDefaultAddress.isChecked();
            UserRepository.setUserAddress(address, isDefault).thenAccept(aVoid -> {
                dismiss();
            });
        });

    }
}
