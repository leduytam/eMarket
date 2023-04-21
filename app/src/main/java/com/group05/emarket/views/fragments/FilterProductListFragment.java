package com.group05.emarket.views.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group05.emarket.Constants;
import com.group05.emarket.R;
import com.group05.emarket.databinding.FragmentFilterProductBinding;

public class FilterProductListFragment extends Fragment {
    private FragmentFilterProductBinding binding;
    private final float[] initialPriceRange;

    public FilterProductListFragment(float[] initialPriceRange) {
        this.initialPriceRange = initialPriceRange;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        var minMaxRange = getResources().getIntArray(R.array.initial_price_range_values);

        binding = FragmentFilterProductBinding.inflate(inflater, container, false);
        binding.rsPrice.setValueFrom((float) minMaxRange[0]);
        binding.rsPrice.setValueTo((float) minMaxRange[1]);
        binding.rsPrice.setValues(initialPriceRange[0], initialPriceRange[1]);

        return binding.getRoot();
    }

    public void reset() {
        if (binding != null) {
            var range = Constants.DEFAULT_FILTER_PRODUCT_PRICE_RANGE;
            binding.rsPrice.setValues(range[0], range[1]);
        }
    }

    public float[] getPriceRange() {
        if (binding == null) {
            return initialPriceRange;
        }

        var values = binding.rsPrice.getValues();
        return new float[]{values.get(0), values.get(1)};
    }
}