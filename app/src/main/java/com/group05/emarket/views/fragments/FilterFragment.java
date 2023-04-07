package com.group05.emarket.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.slider.RangeSlider;
import com.group05.emarket.R;

public class FilterFragment extends Fragment {
    public FilterFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_filter, container, false);

        RangeSlider rsPrice = layout.findViewById(R.id.rs_price);
        return layout;
    }
}