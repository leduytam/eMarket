package com.group05.emarket.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.group05.emarket.R;
import com.group05.emarket.adapters.OrderStatesAdapter;

public class OrderFragment extends Fragment {
    public OrderFragment() {
    }

    public static OrderFragment newInstance() {
        return new OrderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        ViewPager2 viewPager = view.findViewById(R.id.view_pager);

        OrderStatesAdapter adapter = new OrderStatesAdapter(getActivity());
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("Pending");
            } else if (position == 1) {
                tab.setText("Processing");
            } else if (position == 2) {
                tab.setText("Shipping");
            } else if (position == 3) {
                tab.setText("Delivered");
            } else if (position == 4) {
                tab.setText("Cancelled");
            }
        }).attach();


        return view;
    }
}