package com.group05.emarket.views.fragments;
import com.google.android.material.datepicker.MaterialDatePicker;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.group05.emarket.R;
import com.group05.emarket.views.activities.AuthenticationActivity;

public class EditProfileFragment extends Fragment {
    public static  EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    TextView dobTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Context context = getContext();
        return view;
    }
}
