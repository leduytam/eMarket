package com.group05.emarket.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.badge.ExperimentalBadgeUtils;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group05.emarket.R;
import com.group05.emarket.views.fragments.HomeFragment;
import com.group05.emarket.views.fragments.OrderFragment;
import com.group05.emarket.views.fragments.ProfileFragment;
import com.group05.emarket.views.fragments.SearchFragment;

@ExperimentalBadgeUtils
public class LayoutActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private OrderFragment orderFragment;
    private ProfileFragment profileFragment;

    private static FirebaseAuth mAuth;

    private NavigationBarView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        mAuth = FirebaseAuth.getInstance();

        homeFragment = HomeFragment.newInstance();
        searchFragment = SearchFragment.newInstance();
        orderFragment = OrderFragment.newInstance();
        profileFragment = ProfileFragment.newInstance();


        var ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_fragment_container, homeFragment);
        ft.commit();

        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(item -> {
            var id = item.getItemId();
            var ft1 = getSupportFragmentManager().beginTransaction();

            if (id == R.id.action_home) {
                ft1.replace(R.id.fl_fragment_container, homeFragment);
            } else if (id == R.id.action_search) {
                ft1.replace(R.id.fl_fragment_container, searchFragment);
            } else if (id == R.id.action_order) {
                ft1.replace(R.id.fl_fragment_container, orderFragment);
            } else if (id == R.id.action_profile) {
                ft1.replace(R.id.fl_fragment_container, profileFragment);
            }

            ft1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft1.commit();

            return true;
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        var firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            var isVerified = firebaseUser.isEmailVerified();
            if (!isVerified) {
                Intent intent = new Intent(LayoutActivity.this, AuthenticationActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Please verify your email", Toast.LENGTH_SHORT).show();
            }
        } else {
            Intent intent = new Intent(LayoutActivity.this, AuthenticationActivity.class);
            startActivity(intent);
        }

        // get extra data from intent
        Intent intent = getIntent();
        String fragment = intent.getStringExtra("fragment");
        if (fragment != null) {
            switch (fragment) {
                case "home":
                    bottomNav.setSelectedItemId(R.id.action_home);
                    break;
                case "search":
                    bottomNav.setSelectedItemId(R.id.action_search);
                    break;
                case "order":
                    bottomNav.setSelectedItemId(R.id.action_order);
                    break;
                case "profile":
                    bottomNav.setSelectedItemId(R.id.action_profile);
                    break;
            }
        }

    }

}