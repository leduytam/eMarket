package com.group05.emarket.views.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.group05.emarket.viewmodels.OrderViewModel;
import com.group05.emarket.views.fragments.order.OrderCancelledFragment;
import com.group05.emarket.views.fragments.order.OrderDeliveredFragment;
import com.group05.emarket.views.fragments.order.OrderPendingFragment;
import com.group05.emarket.views.fragments.order.OrderShippingFragment;

public class OrderStatesAdapter extends FragmentStateAdapter {
    // 4 states: Pending, Shipping, Delivered, Cancelled


    public OrderStatesAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new OrderPendingFragment();
            case 1:
                return new OrderShippingFragment();
            case 2:
                return new OrderDeliveredFragment();
            case 3:
                return new OrderCancelledFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
