package com.group05.emarket.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.group05.emarket.databinding.DialogAddVoucherBinding;
import com.group05.emarket.models.Voucher;
import com.group05.emarket.repositories.VoucherRepository;

public class InputVoucherDialog extends DialogFragment {
    private VoucherRepository voucherRepository = VoucherRepository.getInstance();
    private OnAppliedListener listener;

    public InputVoucherDialog() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnAppliedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ReviewDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        DialogAddVoucherBinding binding = DialogAddVoucherBinding.bind(getLayoutInflater().inflate(com.group05.emarket.R.layout.dialog_add_voucher, null));

        binding.btnAddVoucher.setOnClickListener(v -> {
            String voucherCode = binding.etVoucherCode.getText().toString();
            voucherRepository.getVoucherDiscount(voucherCode).thenAccept(voucher -> {
                if (voucher != null) {
                    listener.onApplied(voucher);
                    Toast.makeText(getContext(), "Applied voucher successfully", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Invalid voucher code", Toast.LENGTH_SHORT).show();
                }
            });
        });

        binding.btnClose.setOnClickListener(v -> dismiss());

        return dialog;
    }

    public interface OnAppliedListener {
        void onApplied(Voucher voucher);
    }
}
