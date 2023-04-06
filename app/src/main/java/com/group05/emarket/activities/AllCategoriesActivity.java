package com.group05.emarket.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.group05.emarket.MockData;
import com.group05.emarket.R;
import com.group05.emarket.adapters.CategoryAdapter;
import com.group05.emarket.models.Category;

import java.util.ArrayList;

public class AllCategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories);

        MaterialToolbar topBar = findViewById(R.id.top_bar);
        topBar.setNavigationOnClickListener(v -> finish());

        RecyclerView _rvCategories = findViewById(R.id.rv_categories);
        _rvCategories.setAdapter(new CategoryAdapter(this, MockData.getCategories()));
        _rvCategories.setLayoutManager(new GridLayoutManager(this, 5));
    }
}