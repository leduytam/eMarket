package com.group05.emarket.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.badge.ExperimentalBadgeUtils;
import com.google.android.material.navigation.NavigationBarView;
import com.group05.emarket.R;
import com.group05.emarket.fragments.HomeFragment;
import com.group05.emarket.fragments.OrderFragment;
import com.group05.emarket.fragments.ProfileFragment;
import com.group05.emarket.fragments.SearchFragment;

@ExperimentalBadgeUtils public class LayoutActivity extends AppCompatActivity {
    private HomeFragment _homeFragment;
    private SearchFragment _searchFragment;
    private OrderFragment _orderFragment;
    private ProfileFragment _profileFragment;

    private NavigationBarView _bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        _homeFragment = HomeFragment.newInstance();
        _searchFragment = SearchFragment.newInstance();
        _orderFragment = OrderFragment.newInstance();
        _profileFragment = ProfileFragment.newInstance();

        var ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_fragment_container, _homeFragment);
        ft.commit();

        _bottomNav = findViewById(R.id.bottom_nav);
        _bottomNav.setOnItemSelectedListener(item -> {
            var id = item.getItemId();
            var ft1 = getSupportFragmentManager().beginTransaction();

            if (id == R.id.action_home) {
                ft1.replace(R.id.fl_fragment_container, _homeFragment);
            } else if (id == R.id.action_search) {
                ft1.replace(R.id.fl_fragment_container, _searchFragment);
            } else if (id == R.id.action_order) {
                ft1.replace(R.id.fl_fragment_container, _orderFragment);
            } else if (id == R.id.action_profile) {
                ft1.replace(R.id.fl_fragment_container, _profileFragment);
            }

            ft1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft1.commit();

            return true;
        });
    }
}