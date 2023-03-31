package com.group05.emarket;

import android.content.DialogInterface;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class UserProfileFragment extends Fragment {

    private Button btnLogout;

    private void showLogoutDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
        builder.setTitle("Logout account?");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform logout action here
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }
}
