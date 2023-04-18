package com.group05.emarket.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.appbar.MaterialToolbar;
import com.group05.emarket.R;

public class AddPaymentFragment extends Fragment {

    private Context context;

    private EditText momoPhoneNumberInput;
    private LinearLayout masterCardInputsLayout;
    private LinearLayout momoInputsLayout;
    private EditText masterCardNumberInput;
    private EditText masterCardExpiryInput;
    private EditText masterCardCvvInput;
    private TextView paymentInfoTitle;
    private Button submitPaymentButton;

    private String[] paymentMethods = {"Visa", "Momo", "MasterCard", "PayPal", "Apple Pay", "Google Pay"};

    AutoCompleteTextView paymentMethod;

    ArrayAdapter<String> adapterPayments;



    public static  AddPaymentFragment newInstance() {
        return new AddPaymentFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_payment, container, false);
        context = getContext();

        masterCardInputsLayout = view.findViewById(R.id.master_card_inputs_layout);
        momoInputsLayout = view.findViewById(R.id.momo_inputs_layout);

        paymentInfoTitle = view.findViewById(R.id.payment_info_title);


        paymentMethod = view.findViewById(R.id.payment_method);
        adapterPayments = new ArrayAdapter<>(context, R.layout.list_item_payment_option, paymentMethods);
        paymentMethod.setAdapter(adapterPayments);

        paymentMethod.setOnItemClickListener((parent, view1, position, id) -> {

            String payment = parent.getItemAtPosition(position).toString();

            paymentInfoTitle.setVisibility(View.VISIBLE);

            switch (payment) {
                case "Momo":
                momoInputsLayout.setVisibility(View.VISIBLE);
                masterCardInputsLayout.setVisibility(View.GONE);
                break;

                case "MasterCard":
                    momoInputsLayout.setVisibility(View.GONE);
                masterCardInputsLayout.setVisibility(View.VISIBLE);
                break;

                default:
                    momoInputsLayout.setVisibility(View.GONE);
                masterCardInputsLayout.setVisibility(View.GONE);
                paymentInfoTitle.setVisibility(View.GONE);
                break;
            }
        });

        MaterialToolbar topBar = view.findViewById(R.id.top_bar);
        topBar.setNavigationOnClickListener(v -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.popBackStack();
        });

        return view;
    }
}
