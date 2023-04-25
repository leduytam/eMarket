package com.group05.emarket.views.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group05.emarket.databinding.FragmentSortProductBinding;
import com.group05.emarket.enums.ESortProductOption;
import com.group05.emarket.views.adapters.SortProductOptionAdapter;

import java.util.List;

public class SortProductListFragment extends Fragment {
    private SortProductOptionAdapter adapter;
    private Context context;
    private final ESortProductOption initialSortOption;

    public SortProductListFragment(ESortProductOption initialSortOption) {
        this.initialSortOption = initialSortOption;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = context;
        adapter = new SortProductOptionAdapter(context, List.of(
                ESortProductOption.NAME_ASCENDING,
                ESortProductOption.NAME_DESCENDING,
                ESortProductOption.PRICE_ASCENDING,
                ESortProductOption.PRICE_DESCENDING,
                ESortProductOption.HIGHEST_RATED,
                ESortProductOption.LOWEST_RATED
        ));

        if (initialSortOption != null) {
            adapter.setSelectedOption(initialSortOption);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        var binding = FragmentSortProductBinding.inflate(inflater, container, false);

        binding.rvSortOptions.setAdapter(adapter);
        binding.rvSortOptions.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), RecyclerView.VERTICAL);
        binding.rvSortOptions.addItemDecoration(dividerItemDecoration);

        return binding.getRoot();
    }

    public ESortProductOption getSelectedOption() {
        if (adapter == null) {
            return initialSortOption;
        }

        return adapter.getSelectedOption();
    }

    public void reset() {
        if (adapter != null) {
            adapter.setSelectedIndex(0);
        }
    }
}