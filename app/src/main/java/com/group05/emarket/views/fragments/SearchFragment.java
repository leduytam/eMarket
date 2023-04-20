package com.group05.emarket.views.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.search.SearchBar;
import com.group05.emarket.MockData;
import com.group05.emarket.R;
import com.group05.emarket.models.Product;
import com.group05.emarket.views.adapters.ProductAdapter;
import com.group05.emarket.views.adapters.SearchResultsAdapter;
import com.group05.emarket.views.adapters.SearchingCategoryAdapter;
import com.group05.emarket.views.decorations.GridGapItemDecoration;

import java.util.List;

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
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        RecyclerView recyclerCategoriesView = view.findViewById(R.id.rv_search_all_categories);
        var gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

        var gridLayoutSearchManager = new GridLayoutManager(getContext(), 3);
        gridLayoutSearchManager.setOrientation(RecyclerView.VERTICAL);

        recyclerCategoriesView.setLayoutManager(gridLayoutManager);
        recyclerCategoriesView.setAdapter(new SearchingCategoryAdapter(getContext(), MockData.getCategories()));
        recyclerCategoriesView.addItemDecoration(new GridGapItemDecoration(2, 30, true));

        RecyclerView searchResultView = view.findViewById(R.id.rv_search_results);
        searchResultView.setLayoutManager(gridLayoutSearchManager);
        searchResultView.addItemDecoration(new GridGapItemDecoration(3, 30, true));
        searchResultView.setAdapter(new ProductAdapter(getContext(), MockData.getProducts()));

        SearchView searchView = view.findViewById(R.id.search_bar);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (newText == null || newText.isEmpty()) {
                            recyclerCategoriesView.setVisibility(View.VISIBLE);
                            searchResultView.setVisibility(View.GONE);
                        } else {
                            List<Product> products = MockData.getProductsByKeyword(newText);
                            ((ProductAdapter) searchResultView.getAdapter()).setProducts(products);
                            recyclerCategoriesView.setVisibility(View.GONE);
                            searchResultView.setVisibility(View.VISIBLE);
                        }
                        return false;
                    }
                }
        );

        return view;
    }
}