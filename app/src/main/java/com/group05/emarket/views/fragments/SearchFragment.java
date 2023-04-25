package com.group05.emarket.views.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.search.SearchBar;
import com.group05.emarket.MockData;
import com.group05.emarket.R;
import com.group05.emarket.databinding.FragmentSearchBinding;
import com.group05.emarket.enums.EProductListType;
import com.group05.emarket.models.Product;
import com.group05.emarket.viewmodels.SearchViewModel;
import com.group05.emarket.views.activities.ProductListActivity;
import com.group05.emarket.views.adapters.ProductAdapter;
import com.group05.emarket.views.adapters.SearchResultsAdapter;
import com.group05.emarket.views.adapters.SearchingCategoryAdapter;
import com.group05.emarket.views.decorations.GridGapItemDecoration;

import java.util.List;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        SearchViewModel searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        var gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

        var gridLayoutSearchManager = new GridLayoutManager(getContext(), 3);
        gridLayoutSearchManager.setOrientation(RecyclerView.VERTICAL);

        binding.rvSearchAllCategories.setLayoutManager(gridLayoutManager);
        binding.rvSearchAllCategories.addItemDecoration(new GridGapItemDecoration(2, 30, true));

        binding.searchBar.setIconifiedByDefault(false);
        binding.searchBar.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.searchBar.clearFocus();

                Intent intent = new Intent(getContext(), ProductListActivity.class);

                intent.putExtra("title", "Search");
                intent.putExtra("type", EProductListType.SEARCH);
                intent.putExtra("isEnableQuery", true);
                intent.putExtra("isFocusSearch", true);

                startActivity(intent);
            }
        });

        searchViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            binding.pbIsLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        searchViewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            binding.rvSearchAllCategories.setAdapter(new SearchingCategoryAdapter(getContext(), categories));
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}