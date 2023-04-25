package com.group05.emarket.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group05.emarket.databinding.FragmentOverviewCategoriesBinding;
import com.group05.emarket.models.Category;
import com.group05.emarket.views.adapters.CategoryAdapter;
import com.group05.emarket.views.dialogs.AllCategoriesDialog;

import java.util.List;

public class OverviewCategoriesFragment extends Fragment {
    private FragmentOverviewCategoriesBinding binding;
    private List<Category> categories;

    public OverviewCategoriesFragment() {
    }

    public static OverviewCategoriesFragment newInstance() {
        return new OverviewCategoriesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOverviewCategoriesBinding.inflate(inflater, container, false);

        binding.tvSeeAll.setOnClickListener(v -> {
            AllCategoriesDialog dialog = new AllCategoriesDialog(categories);
            dialog.show(getActivity().getSupportFragmentManager(), "all_categories_dialog");
        });

        return binding.getRoot();
    }

    public void setCategories(List<Category> categories) {
        if (binding == null) {
            return;
        }

        this.categories = categories;
        binding.rvCategories.setAdapter(new CategoryAdapter(getContext(), categories));
    }

    public void setIsLoading(boolean isLoading) {
        if (binding == null) {
            return;
        }

        if (isLoading) {
            binding.shimmer.startShimmer();
        } else {
            binding.shimmer.stopShimmer();
        }

        binding.shimmer.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        binding.rvCategories.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }
}