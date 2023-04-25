package com.group05.emarket.views.activities;

import static android.content.ContentValues.TAG;
import static com.group05.emarket.schemas.UsersFirestoreSchema.ADDRESS;
import static com.group05.emarket.schemas.UsersFirestoreSchema.BIRTHDAY;
import static com.group05.emarket.schemas.UsersFirestoreSchema.COLLECTION_NAME;
import static com.group05.emarket.schemas.UsersFirestoreSchema.FULL_NAME;
import static com.group05.emarket.schemas.UsersFirestoreSchema.PHONE_NUMBER;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group05.emarket.databinding.ActivityEditProfileBinding;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    private ActivityEditProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.topBar.setNavigationOnClickListener(v -> finish());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uuid = user.getUid();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection(COLLECTION_NAME).document(uuid);
        documentReference.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String fullName = document.getString(FULL_NAME);
                            String phoneNumber = document.getString(PHONE_NUMBER);
                            String address = document.getString(ADDRESS);
                            String dob = document.getString(BIRTHDAY);
                            binding.etFullName.setText(fullName);
                            binding.etPhoneNumber.setText(phoneNumber);
                            binding.etAddress.setText(address);
                            binding.etDob.setText(dob);
                        }
                    }
                });

        binding.btnUpdate.setOnClickListener(btnView -> {
            String fullName = binding.etFullName.getText().toString();
            String phoneNumber = binding.etPhoneNumber.getText().toString();
            String address = binding.etAddress.getText().toString();
            String dob = binding.etDob.getText().toString();
            Map<String, Object> userMap = new HashMap<>();
            userMap.put(FULL_NAME, fullName);
            userMap.put(PHONE_NUMBER, phoneNumber);
            userMap.put(ADDRESS, address);
            userMap.put(BIRTHDAY, dob);

            documentReference.update(userMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Update profile successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Update profile failed", Toast.LENGTH_SHORT).show();
                }
            });
        });

        binding.etDob.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    (datePicker, y, m, d) -> {
                        String date = d + "/" + m + "/" + y;
                        binding.etDob.setText(date);
                    },
                    year, month, day);

            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });
    }
}