package com.group05.emarket.views.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;
import com.group05.emarket.R;
import com.group05.emarket.databinding.DialogFilterSortBinding;
import com.group05.emarket.enums.ESortProductOption;
import com.group05.emarket.views.adapters.FilterSortViewPagerAdapter;
import com.group05.emarket.views.fragments.FilterProductListFragment;
import com.group05.emarket.views.fragments.SortProductListFragment;

public class FilterSortProductListDialog extends DialogFragment {
    private final OnAppliedListener listener;
    private final float[] priceRange;
    private final ESortProductOption sortOption;

    public FilterSortProductListDialog(float[] priceRange, ESortProductOption sortOption, OnAppliedListener listener) {
        this.listener = listener;
        this.priceRange = priceRange;
        this.sortOption = sortOption;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        DialogFilterSortBinding binding = DialogFilterSortBinding.bind(getLayoutInflater().inflate(R.layout.dialog_filter_sort, null));

        FilterSortViewPagerAdapter adapter = new FilterSortViewPagerAdapter(getActivity(), priceRange, sortOption);
        binding.viewPager.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            if (position == 1) {
                tab.setText("Sorting");
            } else {
                tab.setText("Filter");
            }
        }).attach();

        binding.btnClose.setOnClickListener(v -> dismiss());

        binding.btnReset.setOnClickListener(v -> {
            Fragment currentFragment = adapter.createFragment(binding.viewPager.getCurrentItem());

            if (currentFragment instanceof SortProductListFragment) {
                ((SortProductListFragment) currentFragment).reset();
            } else if (currentFragment instanceof FilterProductListFragment) {
                ((FilterProductListFragment) currentFragment).reset();
            }
        });

        binding.btnApply.setOnClickListener(v -> {
            var priceRange = adapter.getFilterProductListFragment().getPriceRange();
            var selectedSortOption = adapter.getSortingProductListFragment().getSelectedOption();

            if (listener != null) {
                listener.onApplied(priceRange, selectedSortOption);
            }

            dismiss();
        });

        dialog.setContentView(binding.getRoot());

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public interface OnAppliedListener {
        void onApplied(float[] priceRange, ESortProductOption selectedSortOption);
    }
}
