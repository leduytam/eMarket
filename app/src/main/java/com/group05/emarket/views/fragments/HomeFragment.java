package com.group05.emarket.views.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.group05.emarket.R;
import com.group05.emarket.databinding.FragmentHomeBinding;
import com.group05.emarket.enums.EProductListType;
import com.group05.emarket.models.BannerItem;
import com.group05.emarket.viewmodels.AddressViewModel;
import com.group05.emarket.viewmodels.CartViewModel;
import com.group05.emarket.viewmodels.HomeViewModel;
import com.group05.emarket.views.activities.MapActivity;
import com.group05.emarket.views.adapters.BannerPagerAdapter;
import com.group05.emarket.views.adapters.ProductAdapter;
import com.group05.emarket.views.activities.CartActivity;
import com.group05.emarket.views.activities.NotificationActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private CartViewModel cartViewModel;

    private AddressViewModel addressViewModel;
    private FragmentHomeBinding binding;

    private OverviewCategoriesFragment fragmentOverviewCategories;
    private OverviewProductsFragment fragmentOverviewPopularProducts;
    private OverviewProductsFragment fragmentOverviewNewProducts;
    private OverviewProductsFragment fragmentOverviewDiscountProducts;


    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        cartViewModel.fetch();
        addressViewModel.fetch();
    }

    @SuppressLint("UnsafeOptInUsageError")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        Context context = binding.getRoot().getContext();

        fragmentOverviewCategories = OverviewCategoriesFragment.newInstance();
        fragmentOverviewPopularProducts = OverviewProductsFragment.newInstance(
                EProductListType.POPULAR,
                null
        );

        fragmentOverviewNewProducts = OverviewProductsFragment.newInstance(
                EProductListType.NEW,
                null
        );

        fragmentOverviewDiscountProducts = OverviewProductsFragment.newInstance(
                EProductListType.DISCOUNT,
                null
        );

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.add(R.id.fragment_overview_categories, fragmentOverviewCategories);
        ft.add(R.id.fragment_overview_popular_products, fragmentOverviewPopularProducts);
        ft.add(R.id.fragment_overview_new_products, fragmentOverviewNewProducts);
        ft.add(R.id.fragment_overview_discount_products, fragmentOverviewDiscountProducts);
        ft.commit();

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            fragmentOverviewCategories.setCategories(categories);
        });

        homeViewModel.getPopularProducts().observe(getViewLifecycleOwner(), products -> {
            fragmentOverviewPopularProducts.setProducts(products);
        });

        homeViewModel.getNewProducts().observe(getViewLifecycleOwner(), products -> {
            fragmentOverviewNewProducts.setProducts(products);
        });

        homeViewModel.getDiscountProducts().observe(getViewLifecycleOwner(), products -> {
            fragmentOverviewDiscountProducts.setProducts(products);
        });

        homeViewModel.isLoading().observe(getViewLifecycleOwner(), isLoading -> {
            fragmentOverviewCategories.setIsLoading(isLoading);
            fragmentOverviewPopularProducts.setIsLoading(isLoading);
            fragmentOverviewNewProducts.setIsLoading(isLoading);
            fragmentOverviewDiscountProducts.setIsLoading(isLoading);
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

        var cartBadge = BadgeDrawable.create(context);

        BadgeUtils.attachBadgeDrawable(badgeNotification, topBar, topBar.getMenu().findItem(R.id.action_notification).getItemId());
        BadgeUtils.attachBadgeDrawable(cartBadge, topBar, topBar.getMenu().findItem(R.id.action_cart).getItemId());

        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
        cartViewModel.getCartItems().observe(getViewLifecycleOwner(), cartItems -> {
            cartBadge.setNumber(cartItems.size());
            cartBadge.setVisible(cartItems.size() != 0);
        });

        addressViewModel = new ViewModelProvider(requireActivity()).get(AddressViewModel.class);
        addressViewModel.getUserAddress().observe(getViewLifecycleOwner(), userAddress -> {
            if (userAddress != null) {
                binding.btnUserLocation.setText(userAddress.getAddress());
            } else {
                binding.btnUserLocation.setText("There is no address yet");
            }
        });
        binding.btnUserLocation.setOnClickListener(v -> {
            Intent intent = new Intent(context, MapActivity.class);
            intent.putExtra("isHavingDefaultAddress", addressViewModel.getUserAddress().getValue() != null);
            startActivity(intent);
        });

        List<BannerItem> bannerItems = new ArrayList<>();
        var banner1 = new BannerItem(R.drawable.home_banner_1, 0, 20);
        banner1.setPaddingHorizontal(50);
        bannerItems.add(banner1);
        var banner2 = new BannerItem(R.drawable.home_banner_2, 0, 20);
        banner2.setPaddingHorizontal(50);
        bannerItems.add(banner2);
        BannerPagerAdapter pagerAdapter = new BannerPagerAdapter(getActivity(), bannerItems);
        ViewPager viewPager = binding.getRoot().findViewById(R.id.banner_viewpager);
        viewPager.setAdapter(pagerAdapter);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}