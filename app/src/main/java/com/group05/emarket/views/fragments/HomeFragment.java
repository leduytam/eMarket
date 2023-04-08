package com.group05.emarket.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;
import com.group05.emarket.MockData;
import com.group05.emarket.R;
import com.group05.emarket.databinding.FragmentHomeBinding;
import com.group05.emarket.viewmodels.CartViewModel;
import com.group05.emarket.views.adapters.ProductAdapter;
import com.group05.emarket.views.activities.CartActivity;
import com.group05.emarket.views.activities.NotificationActivity;
import com.group05.emarket.views.adapters.CategoryAdapter;
import com.group05.emarket.views.decorations.GridGapItemDecoration;
import com.group05.emarket.views.dialogs.AllCategoriesDialog;

@ExperimentalBadgeUtils
public class HomeFragment extends Fragment {
    private BadgeDrawable cartBadge;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        com.group05.emarket.databinding.FragmentHomeBinding binding = FragmentHomeBinding.inflate(inflater, container, false);
        Context context = binding.getRoot().getContext();

        var gridGapItemDecoration = new GridGapItemDecoration(3, 20, true);

        binding.rvCategories.setAdapter(new CategoryAdapter(context, MockData.getCategories()));

        binding.rvPopularProducts.setAdapter(new ProductAdapter(context, MockData.getProducts().subList(0, 3)));
        binding.rvPopularProducts.addItemDecoration(gridGapItemDecoration);

        binding.rvNewestProducts.setAdapter(new ProductAdapter(context, MockData.getProducts().subList(0, 3)));
        binding.rvNewestProducts.addItemDecoration(gridGapItemDecoration);

        binding.rvDiscountProducts.setAdapter(new ProductAdapter(context, MockData.getProducts().subList(0, 3)));
        binding.rvDiscountProducts.addItemDecoration(gridGapItemDecoration);

        binding.tvSeeAllCategories.setOnClickListener(v -> {
            AllCategoriesDialog dialog = new AllCategoriesDialog();
            dialog.show(getActivity().getSupportFragmentManager(), "all_categories_dialog");
        });


        MaterialToolbar topBar = binding.topBar;
        topBar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            Intent intent;

            if (id == R.id.action_notification) {
                intent = new Intent(context, NotificationActivity.class);
            } else if (id == R.id.action_cart) {
                intent = new Intent(context, CartActivity.class);
            } else {
                return false;
            }

            startActivity(intent);

            return true;
        });

        BadgeDrawable badgeNotification = BadgeDrawable.create(context);
        badgeNotification.clearNumber();

        cartBadge = BadgeDrawable.create(context);

        BadgeUtils.attachBadgeDrawable(badgeNotification, topBar, topBar.getMenu().findItem(R.id.action_notification).getItemId());
        BadgeUtils.attachBadgeDrawable(cartBadge, topBar, topBar.getMenu().findItem(R.id.action_cart).getItemId());

        CartViewModel cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
        cartViewModel.getCartItems().observe(getViewLifecycleOwner(), cartItems -> {
            cartBadge.setNumber(cartItems.size());
            cartBadge.setVisible(cartItems.size() != 0);
        });

        return binding.getRoot();
    }
}