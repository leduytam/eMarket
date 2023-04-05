package com.group05.emarket.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
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
        _rvCategories.setAdapter(new CategoryAdapter(this, _getAllCategories()));
        _rvCategories.setLayoutManager(new GridLayoutManager(this, 5));
    }

    private static ArrayList<Category> _getAllCategories() {
        var categories = new ArrayList<Category>();

        categories.add(new Category("Vegetables", R.drawable.ic_button_vegetable));
        categories.add(new Category("Fruits", R.drawable.ic_button_fruit));
        categories.add(new Category("Meats", R.drawable.ic_button_meat));
        categories.add(new Category("Eggs", R.drawable.ic_button_egg));
        categories.add(new Category("Fishes", R.drawable.ic_button_vegetable));
        categories.add(new Category("Snacks", R.drawable.ic_button_fruit));
        categories.add(new Category("Beverages", R.drawable.ic_button_meat));
        categories.add(new Category("Bakeries", R.drawable.ic_button_egg));

        return categories;
    }
}