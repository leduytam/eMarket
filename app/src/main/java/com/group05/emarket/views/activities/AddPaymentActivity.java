package com.group05.emarket.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.android.material.appbar.MaterialToolbar;
import com.group05.emarket.R;
import com.group05.emarket.databinding.ActivityAddPaymentBinding;

public class AddPaymentActivity extends AppCompatActivity {
    private final String[] PAYMENT_METHODS = {"Visa", "Momo", "MasterCard"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAddPaymentBinding binding = ActivityAddPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        var adapterPayments = new ArrayAdapter<>(this, R.layout.list_item_payment_option, PAYMENT_METHODS);
        binding.paymentMethod.setAdapter(adapterPayments);

        binding.paymentMethod.setOnItemClickListener((parent, view1, position, id) -> {

            String payment = parent.getItemAtPosition(position).toString();

            binding.paymentInfoTitle.setVisibility(View.VISIBLE);

            switch (payment) {
                case "Momo":
                    binding.momoInputsLayout.setVisibility(View.VISIBLE);
                    binding.masterCardInputsLayout.setVisibility(View.GONE);
                    break;

                case "MasterCard":
                    binding.momoInputsLayout.setVisibility(View.GONE);
                    binding.masterCardInputsLayout.setVisibility(View.VISIBLE);
                    break;

                default:
                    binding.momoInputsLayout.setVisibility(View.GONE);
                    binding.masterCardInputsLayout.setVisibility(View.GONE);
                    binding.paymentInfoTitle.setVisibility(View.GONE);
                    break;
            }
        });

        binding.topBar.setNavigationOnClickListener(v -> finish());
    }
}