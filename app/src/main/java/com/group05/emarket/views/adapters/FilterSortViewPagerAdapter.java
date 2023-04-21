package com.group05.emarket.views.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.group05.emarket.enums.SortProductOption;
import com.group05.emarket.views.fragments.FilterProductListFragment;
import com.group05.emarket.views.fragments.SortingProductListFragment;

public class FilterSortViewPagerAdapter extends FragmentStateAdapter {
    private final SortingProductListFragment sortingProductListFragment;
    private final FilterProductListFragment filterProductListFragment;

    public FilterSortViewPagerAdapter(@NonNull FragmentActivity fa, float[] priceRange, SortProductOption sortOption) {
        super(fa);

        sortingProductListFragment = new SortingProductListFragment(sortOption);
        filterProductListFragment = new FilterProductListFragment(priceRange);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return sortingProductListFragment;
        } else {
            return filterProductListFragment;
        }
    }

    public SortingProductListFragment getSortingProductListFragment() {
        return sortingProductListFragment;
    }

    public FilterProductListFragment getFilterProductListFragment() {
        return filterProductListFragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
