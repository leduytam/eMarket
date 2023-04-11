package com.group05.emarket.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group05.emarket.MockData;
import com.group05.emarket.R;
import com.group05.emarket.views.adapters.CategoryAdapter;
import com.group05.emarket.views.adapters.SearchingCategoryAdapter;
import com.group05.emarket.views.decorations.GridGapItemDecoration;

public class SearchFragment extends Fragment {
    public SearchFragment() {
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_search, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_search_all_categories);
        var gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new SearchingCategoryAdapter(getContext(), MockData.getCategories()));
        recyclerView.addItemDecoration(new GridGapItemDecoration(2, 30, true));
        return view;
    }
}