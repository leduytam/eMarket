package com.group05.emarket.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group05.emarket.R;
import com.group05.emarket.databinding.FragmentFavoriteBinding;
import com.group05.emarket.viewmodels.FavoriteViewModel;
import com.group05.emarket.views.activities.LayoutActivity;
import com.group05.emarket.views.adapters.FavoriteProductAdapter;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {
    private FragmentFavoriteBinding binding;
    private FavoriteViewModel favoriteViewModel;

    public FavoriteFragment() {
    }

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        favoriteViewModel = new ViewModelProvider(this, new FavoriteViewModel.Factory()).get(FavoriteViewModel.class);
        favoriteViewModel.fetchFavoriteProducts();
        RecyclerView recyclerFavoriteProductsView = binding.llFavoriteProductsContainer.findViewById(R.id.rv_favorite_products);
        recyclerFavoriteProductsView.setLayoutManager(new LinearLayoutManager(getContext()));
        FavoriteProductAdapter adapter = new FavoriteProductAdapter(new ArrayList<>(), favoriteViewModel, getContext());
        recyclerFavoriteProductsView.setAdapter(adapter);
        favoriteViewModel.getFavoriteProducts().observe(getViewLifecycleOwner(), products -> {
            binding.llEmptyFavoriteProductsContainer.setVisibility(products.isEmpty() ? View.VISIBLE : View.GONE);
            binding.llFavoriteProductsContainer.setVisibility(products.isEmpty() ? View.GONE : View.VISIBLE);
            adapter.setFavoriteProducts(products);
        });

        favoriteViewModel.isLoading().observe(getViewLifecycleOwner(), isLoading -> {
            binding.llFavoriteProductsLoaderContainer.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        binding.btnEmptyFavorite.setOnClickListener(v -> {
            LayoutActivity activity = (LayoutActivity) getActivity();
            if (activity != null) {
                activity.switchTab(R.id.action_home);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        favoriteViewModel.fetchFavoriteProducts();
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
