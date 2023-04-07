package com.group05.emarket.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.group05.emarket.fragments.FilterFragment;
import com.group05.emarket.fragments.SortingFragment;

public class FilterSortingViewPagerAdapter extends FragmentStateAdapter {
    public FilterSortingViewPagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new SortingFragment();
        } else {
            return new FilterFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
