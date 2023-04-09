package com.group05.emarket.views.fragments;
import static android.content.ContentValues.TAG;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.group05.emarket.R;
import com.group05.emarket.views.activities.AuthenticationActivity;

import java.util.Calendar;

public class EditProfileFragment extends Fragment {

    public DatePickerDialog.OnDateSetListener dateSetListener;

    public TextInputEditText tiDOb;

    private Context context;
    public static  EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        context = getContext();

        MaterialToolbar topBar = view.findViewById(R.id.top_bar);
        topBar.setNavigationOnClickListener(v -> requireActivity().finish());

        tiDOb = view.findViewById(R.id.dob_edit_text);
        tiDOb.setInputType(InputType.TYPE_NULL);
        tiDOb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        context,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);

                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Log.d(TAG, "onSetDate: date" + year + "/" + month + "/" + day );

                String date = month + "/" + day + "/" + year;
                tiDOb.setText(date);
            }
        };
        return view;
    }
}
