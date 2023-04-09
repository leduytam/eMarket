package com.group05.emarket.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.group05.emarket.R;
import com.group05.emarket.views.activities.AuthenticationActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.group05.emarket.views.activities.LoginActivity;
import com.group05.emarket.views.activities.SignUpActivity;

public class ProfileFragment extends Fragment {
    private Button btnLogout;
    private Context context;
    private static FirebaseAuth mAuth;
    public ProfileFragment() {
    }
    MaterialAlertDialogBuilder alertDialogBuilder;


    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        alertDialogBuilder = new MaterialAlertDialogBuilder(requireContext());
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        context = getContext();
        Button btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> onLogout());
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
}