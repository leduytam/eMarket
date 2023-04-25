package com.group05.emarket.views.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.group05.emarket.enums.ESortProductOption;
import com.group05.emarket.views.fragments.FilterProductListFragment;
import com.group05.emarket.views.fragments.SortProductListFragment;

public class FilterSortViewPagerAdapter extends FragmentStateAdapter {
    private final SortProductListFragment sortProductListFragment;
    private final FilterProductListFragment filterProductListFragment;

    public FilterSortViewPagerAdapter(@NonNull FragmentActivity fa, float[] priceRange, ESortProductOption sortOption) {
        super(fa);

        sortProductListFragment = new SortProductListFragment(sortOption);
        filterProductListFragment = new FilterProductListFragment(priceRange);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return sortProductListFragment;
        } else {
            return filterProductListFragment;
        }
    }

    public SortProductListFragment getSortingProductListFragment() {
        return sortProductListFragment;
    }

    public FilterProductListFragment getFilterProductListFragment() {
        return filterProductListFragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
