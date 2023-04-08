package com.group05.emarket.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.group05.emarket.fragments.order.OrderCancelledFragment;
import com.group05.emarket.fragments.order.OrderDeliveredFragment;
import com.group05.emarket.fragments.order.OrderPendingFragment;
import com.group05.emarket.fragments.order.OrderProcessingFragment;
import com.group05.emarket.fragments.order.OrderShippingFragment;

public class OrderStatesAdapter extends FragmentStateAdapter {
    // 5 states: Pending, Processing, Shipping, Delivered, Cancelled

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
                return new OrderProcessingFragment();
            case 2:
                return new OrderShippingFragment();
            case 3:
                return new OrderDeliveredFragment();
            case 4:
                return new OrderCancelledFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
