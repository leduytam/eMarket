package com.group05.emarket.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
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
import com.group05.emarket.databinding.FragmentHomeBinding;
import com.group05.emarket.views.adapters.ProductAdapter;
import com.group05.emarket.views.activities.CartActivity;
import com.group05.emarket.views.activities.NotificationActivity;
import com.group05.emarket.views.adapters.CategoryAdapter;
import com.group05.emarket.views.decorations.GridGapItemDecoration;
import com.group05.emarket.views.dialogs.AllCategoriesDialog;

@ExperimentalBadgeUtils
public class HomeFragment extends Fragment {
    private Context _context;

    private BadgeDrawable badgeNotification;
    private BadgeDrawable badgeCart;

    private FragmentHomeBinding binding;

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
        View layout = inflater.inflate(R.layout.fragment_home, container, false);

        var gridGapItemDecoration = new GridGapItemDecoration(3, 20, true);

        RecyclerView _rvCategories = layout.findViewById(R.id.rv_categories);
        _rvCategories.setAdapter(new CategoryAdapter(_context, MockData.getCategories()));

        RecyclerView rvPopularProducts = layout.findViewById(R.id.rv_popular_products);
        rvPopularProducts.setAdapter(new ProductAdapter(_context, MockData.getProducts().subList(0, 3)));
        rvPopularProducts.addItemDecoration(gridGapItemDecoration);

        RecyclerView rvNewestProducts = layout.findViewById(R.id.rv_newest_products);
        rvNewestProducts.setAdapter(new ProductAdapter(_context, MockData.getProducts().subList(0, 3)));
        rvNewestProducts.addItemDecoration(gridGapItemDecoration);

        RecyclerView rvDiscountProducts = layout.findViewById(R.id.rv_discount_products);
        rvDiscountProducts.setAdapter(new ProductAdapter(_context, MockData.getProducts().subList(0, 3)));
        rvDiscountProducts.addItemDecoration(gridGapItemDecoration);

        TextView tvSeeAllCategories = layout.findViewById(R.id.tv_see_all_categories);
        tvSeeAllCategories.setOnClickListener(v -> {
            AllCategoriesDialog dialog = new AllCategoriesDialog();
            dialog.show(getActivity().getSupportFragmentManager(), "all_categories_dialog");
        });

        Toolbar _topBar = layout.findViewById(R.id.top_bar);

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

        badgeNotification = BadgeDrawable.create(_context);
        badgeNotification.clearNumber();

        badgeCart = BadgeDrawable.create(_context);
        badgeCart.clearNumber();

        BadgeUtils.attachBadgeDrawable(badgeNotification, _topBar, _topBar.getMenu().findItem(R.id.action_notification).getItemId());
        BadgeUtils.attachBadgeDrawable(badgeCart, _topBar, _topBar.getMenu().findItem(R.id.action_cart).getItemId());

        return layout;
    }
}