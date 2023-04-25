package com.group05.emarket.views.dialogs;

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
import com.group05.emarket.models.Category;
import com.group05.emarket.views.adapters.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class AllCategoriesDialog extends DialogFragment {
    private final List<Category> categories;

    public AllCategoriesDialog(List<Category> categories) {
        this.categories = categories == null ? new ArrayList<>() : categories;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), getTheme());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = getLayoutInflater().inflate(R.layout.dialog_all_categories, null);
        dialog.setContentView(view);

        Button btnClose = view.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(v -> dismiss());

        CategoryAdapter adapter = new CategoryAdapter(getActivity(), categories);
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
