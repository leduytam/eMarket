package com.group05.emarket.views.activities;

import static com.group05.emarket.schemas.UsersSchema.ADDRESS;
import static com.group05.emarket.schemas.UsersSchema.BIRTHDAY;
import static com.group05.emarket.schemas.UsersSchema.COLLECTION_NAME;
import static com.group05.emarket.schemas.UsersSchema.FULL_NAME;
import static com.group05.emarket.schemas.UsersSchema.PHONE_NUMBER;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group05.emarket.R;
import com.group05.emarket.databinding.ActivityEditProfileBinding;
import com.group05.emarket.repositories.AddressRepository;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    private ActivityEditProfileBinding binding;
    private static AddressRepository addressRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addressRepository = AddressRepository.getInstance();

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
                            String dob = document.getString(BIRTHDAY);
                            binding.etFullName.setText(fullName);
                            binding.etPhoneNumber.setText(phoneNumber);
                            addressRepository.getAddress().thenAccept(address -> {
                                if (address != null) {
                                    binding.etAddress.setText(address.getAddress());
                                }
                                else {
                                    binding.etAddress.setText("You have not set your address yet");
                                    binding.etAddress.setTextColor(getResources().getColor(R.color.gray_300));
                                }
                            });
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

        binding.btnAddressGoToMap.setOnClickListener(view -> {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });
    }
}