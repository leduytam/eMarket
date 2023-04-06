package com.group05.emarket.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;
import com.group05.emarket.MockData;
import com.group05.emarket.R;
import com.group05.emarket.adapters.ProductAdapter;
import com.group05.emarket.activities.AllCategoriesActivity;
import com.group05.emarket.activities.CartActivity;
import com.group05.emarket.activities.NotificationActivity;
import com.group05.emarket.adapters.CategoryAdapter;
import com.group05.emarket.models.Category;
import com.group05.emarket.models.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

@ExperimentalBadgeUtils
public class HomeFragment extends Fragment {
    private Context _context;
    private Toolbar _topBar;

    private BadgeDrawable _badgeNotification;
    private BadgeDrawable _badgeCart;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_home, container, false);

        var productItemDecoration = new ProductAdapter.ProductItemDecoration(3, 20, false);

        RecyclerView _rvCategories = layout.findViewById(R.id.rv_categories);
        _rvCategories.setAdapter(new CategoryAdapter(_context, MockData.getCategories()));
        _rvCategories.setLayoutManager(new LinearLayoutManager(_context, LinearLayoutManager.HORIZONTAL, false));

        RecyclerView rvPopularProducts = layout.findViewById(R.id.rv_popular_products);
        rvPopularProducts.setAdapter(new ProductAdapter(_context, MockData.getProducts().subList(0, 3)));
        rvPopularProducts.setLayoutManager(new GridLayoutManager(_context, 3));
        rvPopularProducts.addItemDecoration(productItemDecoration);

        RecyclerView rvNewestProducts = layout.findViewById(R.id.rv_newest_products);
        rvNewestProducts.setAdapter(new ProductAdapter(_context, MockData.getProducts().subList(0, 3)));
        rvNewestProducts.setLayoutManager(new GridLayoutManager(_context, 3));
        rvNewestProducts.addItemDecoration(productItemDecoration);

        RecyclerView rvDiscountProducts = layout.findViewById(R.id.rv_discount_products);
        rvDiscountProducts.setAdapter(new ProductAdapter(_context, MockData.getProducts().subList(0, 3)));
        rvDiscountProducts.setLayoutManager(new GridLayoutManager(_context, 3));
        rvDiscountProducts.addItemDecoration(productItemDecoration);

        TextView tvSeeAllCategories = layout.findViewById(R.id.tv_see_all_categories);
        tvSeeAllCategories.setOnClickListener(v -> {
            Intent intent = new Intent(_context, AllCategoriesActivity.class);
            startActivity(intent);
        });

        _topBar = layout.findViewById(R.id.top_bar);

        _topBar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            Intent intent;

            if (id == R.id.action_notification) {
                intent = new Intent(_context, NotificationActivity.class);
            } else if (id == R.id.action_cart) {
                intent = new Intent(_context, CartActivity.class);
            } else {
                return false;
            }

            startActivity(intent);

            return true;
        });

        _badgeNotification = BadgeDrawable.create(_context);
        _badgeNotification.clearNumber();

        _badgeCart = BadgeDrawable.create(_context);
        _badgeCart.clearNumber();

        BadgeUtils.attachBadgeDrawable(_badgeNotification, _topBar, _topBar.getMenu().findItem(R.id.action_notification).getItemId());
        BadgeUtils.attachBadgeDrawable(_badgeCart, _topBar, _topBar.getMenu().findItem(R.id.action_cart).getItemId());

        return layout;
    }
}