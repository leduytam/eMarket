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
import com.group05.emarket.enums.SortProductOption;
import com.group05.emarket.views.adapters.SortProductOptionAdapter;

import java.util.List;

public class SortingProductListFragment extends Fragment {
    private SortProductOptionAdapter adapter;
    private Context context;
    private final SortProductOption initialSortOption;

    public SortingProductListFragment(SortProductOption initialSortOption) {
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
                SortProductOption.NEWEST,
                SortProductOption.OLDEST,
                SortProductOption.NAME_ASCENDING,
                SortProductOption.NAME_DESCENDING,
                SortProductOption.PRICE_ASCENDING,
                SortProductOption.PRICE_DESCENDING
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

    public SortProductOption getSelectedOption() {
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