package com.group05.emarket.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.group05.emarket.R;
import com.group05.emarket.databinding.ActivityLayoutBinding;
import com.group05.emarket.views.fragments.HomeFragment;
import com.group05.emarket.views.fragments.OrderFragment;
import com.group05.emarket.views.fragments.ProfileFragment;
import com.group05.emarket.views.fragments.SearchFragment;

public class LayoutActivity extends AppCompatActivity {
    private ActivityLayoutBinding binding;
    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private OrderFragment orderFragment;
    private ProfileFragment profileFragment;
    private static FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        homeFragment = HomeFragment.newInstance();
        searchFragment = SearchFragment.newInstance();
        orderFragment = OrderFragment.newInstance();
        profileFragment = ProfileFragment.newInstance();

        binding.viewPager.setUserInputEnabled(false);
        binding.viewPager.setOffscreenPageLimit(4);
        binding.viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        return homeFragment;
                    case 1:
                        return searchFragment;
                    case 2:
                        return orderFragment;
                    case 3:
                        return profileFragment;
                    default:
                        throw new IllegalArgumentException("Invalid position: " + position);
                }
            }

            @Override
            public int getItemCount() {
                return 4;
            }
        });

        binding.bottomNav.setOnItemSelectedListener(item -> {
            var id = item.getItemId();

            if (id == R.id.action_home) {
                binding.viewPager.setCurrentItem(0, false);
            } else if (id == R.id.action_search) {
                binding.viewPager.setCurrentItem(1, false);
            } else if (id == R.id.action_order) {
                binding.viewPager.setCurrentItem(2, false);
            } else if (id == R.id.action_profile) {
                binding.viewPager.setCurrentItem(3, false);
            }

            return true;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        String fragment = intent.getStringExtra("fragment");

        if (fragment != null) {
            switch (fragment) {
                case "home":
                    binding.bottomNav.setSelectedItemId(R.id.action_home);
                    break;
                case "search":
                    binding.bottomNav.setSelectedItemId(R.id.action_search);
                    break;
                case "order":
                    binding.bottomNav.setSelectedItemId(R.id.action_order);
                    break;
                case "profile":
                    binding.bottomNav.setSelectedItemId(R.id.action_profile);
                    break;
            }
        }
    }
}