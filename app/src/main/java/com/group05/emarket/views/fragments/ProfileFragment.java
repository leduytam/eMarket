package com.group05.emarket.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.group05.emarket.R;
import com.group05.emarket.views.activities.AuthenticationActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ProfileFragment extends Fragment {
    private Button btnLogout;
    private Context context;

    private LinearLayout editProfileLayout;
    private LinearLayout allHistoryLayout;
    private LinearLayout helpLayout;
    private LinearLayout aboutLayout;
    private static FirebaseAuth mAuth;
    private MaterialAlertDialogBuilder alertDialogBuilder;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
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

        btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> onLogout());

        editProfileLayout = view.findViewById(R.id.editProfile_button);
        editProfileLayout.setOnClickListener(v -> onClickEditProfile());

        allHistoryLayout = view.findViewById(R.id.allHistory_button);
        allHistoryLayout.setOnClickListener(v -> onClickAllHistory());

        helpLayout = view.findViewById(R.id.help_button);
        helpLayout.setOnClickListener(v -> onClickHelp());

        aboutLayout = view.findViewById(R.id.about_button);
        aboutLayout.setOnClickListener(v -> onClickAbout());


        return view;
    }

    private void onLogout() {
        alertDialogBuilder.setTitle("Do you want to log out account?").setPositiveButton("Log out", (dialog, which) -> {
            mAuth.signOut();
            Intent intent = new Intent(context, AuthenticationActivity.class);
            startActivity(intent);
        }).setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        }).show();
    }

    private void onClickEditProfile() {
        int fragmentId = this.getId();
        EditProfileFragment editProfileFragment = new EditProfileFragment();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(fragmentId, editProfileFragment)
                .addToBackStack(null)
                .commit();
    }

    private void onClickAllHistory() {
        int fragmentId = this.getId();
        AllHistoryFragment allHistoryFragment = new AllHistoryFragment();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(fragmentId, allHistoryFragment)
                .addToBackStack(null)
                .commit();
    }

    private void onClickHelp() {
        int fragmentId = this.getId();
        HelpFragment helpFragment = new HelpFragment();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(fragmentId, helpFragment)
                .addToBackStack(null)
                .commit();
    }

    private void onClickAbout() {
        int fragmentId = this.getId();
        AboutFragment aboutFragment = new AboutFragment();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(fragmentId, aboutFragment)
                .addToBackStack(null)
                .commit();
    }

}
