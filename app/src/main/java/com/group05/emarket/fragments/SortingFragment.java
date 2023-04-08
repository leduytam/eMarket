package com.group05.emarket.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.group05.emarket.R;
import com.group05.emarket.adapters.SortOptionAdapter;

import java.util.ArrayList;
import java.util.List;

public class SortingFragment extends Fragment {
    public SortingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_sorting, container, false);

        String[] options = new String[]{"Name (A-Z)", "Name (Z-A)", "Price (Ascending)", "Price (Descending)"};

        RecyclerView rvSortOptions = layout.findViewById(R.id.rv_sort_options);
        SortOptionAdapter adapter = new SortOptionAdapter(getContext(), List.of(options));
        rvSortOptions.setAdapter(adapter);
        rvSortOptions.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), RecyclerView.VERTICAL);
        rvSortOptions.addItemDecoration(dividerItemDecoration);

        return layout;
    }
}