package com.group05.emarket.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.group05.emarket.R;
import com.group05.emarket.adapters.CategoryAdapter;
import com.group05.emarket.models.Category;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView _rvCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        _rvCategories = findViewById(R.id.rv_categories);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, _createCategories());
        _rvCategories.setAdapter(categoryAdapter);
        _rvCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private static ArrayList<Category> _createCategories() {
        ArrayList<Category> categories = new ArrayList<>();

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