package com.group05.emarket.fragments;

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
import com.group05.emarket.activities.AuthenticationActivity;

public class ProfileFragment extends Fragment {
    private Button btnLogout;
    private Context _context;
    private static FirebaseAuth mAuth;
    public ProfileFragment() {
    }


    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        _context = getContext();
        btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> onLogout());
        return view;
    }


    private void onLogout() {
        mAuth.signOut();
        Intent intent = new Intent(_context, AuthenticationActivity.class);
        startActivity(intent);
    }
}