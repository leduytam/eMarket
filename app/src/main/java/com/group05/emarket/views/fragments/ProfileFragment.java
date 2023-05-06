package com.group05.emarket.views.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.group05.emarket.R;
import com.group05.emarket.repositories.UserRepository;
import com.group05.emarket.views.activities.AboutActivity;
import com.group05.emarket.views.activities.AuthenticationActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.group05.emarket.views.activities.EditProfileActivity;
import com.group05.emarket.views.activities.HelpActivity;
import com.group05.emarket.views.activities.WalletActivity;

public class ProfileFragment extends Fragment {
    private TextView tvFullName, tvShortBio;
    private Button btnLogout;
    private Context context;

    private LinearLayout editProfileLayout;
    private LinearLayout helpLayout;
    private LinearLayout aboutLayout;

    private LinearLayout walletLayout;

    private ShimmerFrameLayout shimmer;
    private static FirebaseAuth mAuth;
    private MaterialAlertDialogBuilder alertDialogBuilder;


    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onStart() {
        super.onStart();

        tvFullName = getView().findViewById(R.id.user_profile_name);
        tvShortBio = getView().findViewById(R.id.user_profile_short_bio);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uuid = user.getUid();
        UserRepository.getUser(uuid).thenAccept(u -> {
            shimmer.stopShimmer();
            shimmer.setVisibility(View.GONE);
            tvFullName.setText(u.getFullName());
            tvShortBio.setText(u.getEmail());
        }).exceptionally(e -> {
            shimmer.stopShimmer();
            shimmer.setVisibility(View.GONE);
            return null;
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        alertDialogBuilder = new MaterialAlertDialogBuilder(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        context = getContext();
        shimmer = view.findViewById(R.id.user_profile_simmer);
        shimmer.startShimmer();
        btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> onLogout());

        editProfileLayout = view.findViewById(R.id.editProfile_button);
        editProfileLayout.setOnClickListener(v -> onClickEditProfile());

        helpLayout = view.findViewById(R.id.help_button);
        helpLayout.setOnClickListener(v -> onClickHelp());

        aboutLayout = view.findViewById(R.id.about_button);
        aboutLayout.setOnClickListener(v -> onClickAbout());

        walletLayout = view.findViewById(R.id.wallet_button);
        walletLayout.setOnClickListener(v -> onClickWallet());


        return view;
    }

    private void onLogout() {
        alertDialogBuilder.setBackground(AppCompatResources.getDrawable(context, R.drawable.background_dialog_alert)).setTitle("Do you want to log out account?").setPositiveButton("Log out", (dialog, which) -> {
            mAuth.signOut();
            Intent intent = new Intent(context, AuthenticationActivity.class);
            startActivity(intent);
        }).setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        }).show();
    }

    private void onClickEditProfile() {
        Intent intent = new Intent(context, EditProfileActivity.class);
        startActivity(intent);
    }

    private void onClickHelp() {
        Intent intent = new Intent(context, HelpActivity.class);
        startActivity(intent);
    }

    private void onClickAbout() {
        Intent intent = new Intent(context, AboutActivity.class);
        startActivity(intent);
    }

    private void onClickWallet() {
        Intent intent = new Intent(context, WalletActivity.class);
        startActivity(intent);
    }
}
