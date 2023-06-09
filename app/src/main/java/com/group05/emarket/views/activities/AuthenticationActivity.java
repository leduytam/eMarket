package com.group05.emarket.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.group05.emarket.R;
import com.group05.emarket.views.adapters.BannerPagerAdapter;
import com.group05.emarket.models.BannerItem;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class AuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        Button buttonSignUp = findViewById(R.id.signup_button);
        Button buttonLogin = findViewById(R.id.login_button);

        List<BannerItem> bannerItems = new ArrayList<>();
        bannerItems.add(new BannerItem(R.drawable.banner_1, R.drawable.background_banner_item));
        bannerItems.add(new BannerItem(R.drawable.banner_2, R.drawable.background_banner_item));
        bannerItems.add(new BannerItem(R.drawable.banner_3, R.drawable.background_banner_item));

        BannerPagerAdapter pagerAdapter = new BannerPagerAdapter(this, bannerItems);
        ViewPager viewPager = findViewById(R.id.banner_viewpager);
        viewPager.setAdapter(pagerAdapter);

        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        buttonSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(AuthenticationActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        buttonLogin.setOnClickListener(v -> {
            Intent intent = new Intent(AuthenticationActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        var auth = FirebaseAuth.getInstance();
        var firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
            var isVerified = firebaseUser.isEmailVerified();
            if (!isVerified) {
                Toast.makeText(this, "Please verify your email", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, LayoutActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
