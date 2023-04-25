package com.group05.emarket.views.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group05.emarket.R;
import com.group05.emarket.databinding.FragmentOverviewProductsBinding;
import com.group05.emarket.enums.EProductListType;
import com.group05.emarket.models.Product;
import com.group05.emarket.views.activities.ProductListActivity;
import com.group05.emarket.views.adapters.ProductAdapter;
import com.group05.emarket.views.decorations.GridGapItemDecoration;

import java.util.List;

public class OverviewProductsFragment extends Fragment {
    private EProductListType type;
    private Product product;
    private FragmentOverviewProductsBinding binding;

    public OverviewProductsFragment() {
    }

    public static OverviewProductsFragment newInstance(EProductListType type, Product product) {
        OverviewProductsFragment fragment = new OverviewProductsFragment();
        Bundle args = new Bundle();

        args.putSerializable("type", type);
        args.putParcelable("product", product);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        if (getArguments() != null) {
            type = (EProductListType) args.getSerializable("type");
            product = args.getParcelable("product");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOverviewProductsBinding.inflate(inflater, container, false);

        binding.shimmer.startShimmer();

        var title = "";

        if (type == EProductListType.POPULAR) {
            title = getString(R.string.fragment_overview_products_popular);
        } else if (type == EProductListType.NEW) {
            title = getString(R.string.fragment_overview_products_new);
        } else if (type == EProductListType.DISCOUNT) {
            title = getString(R.string.fragment_overview_products_discount);
        } else if (type == EProductListType.RELATED) {
            title = getString(R.string.fragment_overview_products_related);
        }

        binding.tvTitle.setText(title);

        String finalTitle = title;
        binding.tvSeeAll.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ProductListActivity.class);
            intent.putExtra("type", type);
            intent.putExtra("title", finalTitle);
            intent.putExtra("isEnableQuery", false);

            if (product != null) {
                intent.putExtra("categoryId", product.getCategory().getId());
            }

            startActivity(intent);
        });

        var gridGapItemDecoration = new GridGapItemDecoration(3, 20, true);
        binding.rvProducts.addItemDecoration(gridGapItemDecoration);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setProducts(List<Product> products) {
        binding.rvProducts.setAdapter(new ProductAdapter(getContext(), products));
    }

    public void setIsLoading(boolean isLoading) {
        if (binding != null) {
            binding.shimmer.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            binding.rvProducts.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        }
    }
}