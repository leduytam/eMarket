package com.group05.emarket.views.fragments;
import static android.content.ContentValues.TAG;

import static com.group05.emarket.schemas.UsersFirestoreSchema.ADDRESS;
import static com.group05.emarket.schemas.UsersFirestoreSchema.BIRTHDAY;
import static com.group05.emarket.schemas.UsersFirestoreSchema.COLLECTION_NAME;
import static com.group05.emarket.schemas.UsersFirestoreSchema.EMAIL;
import static com.group05.emarket.schemas.UsersFirestoreSchema.FULL_NAME;
import static com.group05.emarket.schemas.UsersFirestoreSchema.PHONE_NUMBER;

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
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group05.emarket.R;
import com.group05.emarket.views.activities.AuthenticationActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditProfileFragment extends Fragment {

    public DatePickerDialog.OnDateSetListener dateSetListener;

    public TextInputEditText tiDOb;

    private Context context;
    private EditText etFullName, etPhoneNumber, etAddress;
    private Button btnUpdate;
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
        topBar.setNavigationOnClickListener(v -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.popBackStack();
        });

        etFullName = view.findViewById(R.id.full_name_edit_text);
        etPhoneNumber = view.findViewById(R.id.phone_edit_text);
        etAddress = view.findViewById(R.id.address_edit_text);
        btnUpdate = view.findViewById(R.id.editProfile_button);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uuid = user.getUid();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection(COLLECTION_NAME).document(uuid);
        documentReference.get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        DocumentSnapshot document = task.getResult();
                        if(document.exists()){
                            String fullName = document.getString(FULL_NAME);
                            String phoneNumber = document.getString(PHONE_NUMBER);
                            String address = document.getString(ADDRESS);
                            String dob = document.getString(BIRTHDAY);
                            etFullName.setText(fullName);
                            etPhoneNumber.setText(phoneNumber);
                            etAddress.setText(address);
                            tiDOb.setText(dob);
                        }
                    }
                });

        tiDOb = view.findViewById(R.id.dob_edit_text);
        tiDOb.setInputType(InputType.TYPE_NULL);
        btnUpdate.setOnClickListener(btnView -> {
            String fullName = etFullName.getText().toString();
            String phoneNumber = etPhoneNumber.getText().toString();
            String address = etAddress.getText().toString();
            String dob = tiDOb.getText().toString();
            Map<String, Object> userMap = new HashMap<>();
            userMap.put(FULL_NAME, fullName);
            userMap.put(PHONE_NUMBER, phoneNumber);
            userMap.put(ADDRESS, address);
            userMap.put(BIRTHDAY, dob);

            documentReference.update(userMap);

        });
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
