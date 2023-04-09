package com.group05.emarket.views.fragments.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group05.emarket.R;

public class OrderProcessingFragment extends Fragment {
    public OrderProcessingFragment() {
    }

    public static OrderProcessingFragment newInstance() {
        return new OrderProcessingFragment();
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
        rvSortOptions.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), RecyclerView.VERTICAL);
        rvSortOptions.addItemDecoration(dividerItemDecoration);

        return layout;
    }
}
