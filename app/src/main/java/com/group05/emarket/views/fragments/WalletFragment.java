package com.group05.emarket.views.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.group05.emarket.MockData;
import com.group05.emarket.R;
import com.group05.emarket.models.Order;
import com.group05.emarket.models.Payment;
import com.group05.emarket.views.adapters.HistoryItemAdapter;
import com.group05.emarket.views.adapters.OrderItemAdapter;
import com.group05.emarket.views.adapters.PaymentItemAdapter;

import java.util.List;

public class WalletFragment extends Fragment {

    private Context context;

    private Button addButton;

    public static  WalletFragment newInstance() {
        return new WalletFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        context = getContext();

        MaterialToolbar topBar = view.findViewById(R.id.top_bar);
        topBar.setNavigationOnClickListener(v -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.popBackStack();
        });

        addButton = view.findViewById(R.id.add_payment_button);
        addButton.setOnClickListener(v -> onClickAddPayment());

        List<Payment> payments;
        payments = MockData.getPayments();
        RecyclerView recyclerPaymentsView = view.findViewById(R.id.ll_payments_container).findViewById(R.id.rv_payments);
        recyclerPaymentsView.setAdapter(new PaymentItemAdapter(getContext(), payments));
        recyclerPaymentsView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerPaymentsView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        List<Order> pendingOrders;
        pendingOrders = MockData.getOrders();
        RecyclerView recyclerOrdersView = view.findViewById(R.id.rv_history);
        recyclerOrdersView.setAdapter(new HistoryItemAdapter(getContext(), pendingOrders));
        recyclerOrdersView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerOrdersView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));


        return view;
    }

    private void onClickAddPayment() {
        int fragmentId = this.getId();
        AddPaymentFragment addPaymentFragment = new AddPaymentFragment();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(fragmentId, addPaymentFragment)
                .addToBackStack(null)
                .commit();
    }
}
