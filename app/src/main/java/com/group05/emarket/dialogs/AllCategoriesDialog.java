package com.group05.emarket.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group05.emarket.MockData;
import com.group05.emarket.R;
import com.group05.emarket.adapters.CategoryAdapter;

public class AllCategoriesDialog extends DialogFragment {
    public AllCategoriesDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.Dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_all_categories, null);
        dialog.setContentView(view);

        Button btnClose = view.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(v -> dismiss());

        CategoryAdapter adapter = new CategoryAdapter(getActivity(), MockData.getCategories());
        RecyclerView rvAllCategories = view.findViewById(R.id.rv_all_categories);
        rvAllCategories.setAdapter(adapter);
        rvAllCategories.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
