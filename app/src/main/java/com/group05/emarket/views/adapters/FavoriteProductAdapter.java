package com.group05.emarket.views.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group05.emarket.databinding.ListItemCartBinding;
import com.group05.emarket.databinding.RecycleItemFavoriteProductBinding;
import com.group05.emarket.models.Product;
import com.group05.emarket.viewmodels.FavoriteViewModel;
import com.group05.emarket.views.activities.ProductDetailActivity;

import java.util.List;

public class FavoriteProductAdapter extends RecyclerView.Adapter<FavoriteProductAdapter.ViewHolder> {
    private final List<Product> favoriteProducts;
    private final FavoriteViewModel viewModel;

    private Context context;


    public FavoriteProductAdapter(List<Product> favoriteProducts, FavoriteViewModel viewModel, Context context) {
        this.favoriteProducts = favoriteProducts;
        this.viewModel = viewModel;
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setFavoriteProducts(List<Product> favoriteProducts) {
        this.favoriteProducts.clear();
        this.favoriteProducts.addAll(favoriteProducts);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecycleItemFavoriteProductBinding binding = RecycleItemFavoriteProductBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setFavoriteProduct(favoriteProducts.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return favoriteProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecycleItemFavoriteProductBinding binding;

        public ViewHolder(@NonNull RecycleItemFavoriteProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.tvFavoritePrice.setPaintFlags(binding.tvFavoritePrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            binding.tvFavoriteProductName.setOnClickListener(v -> {
                Product product = binding.getFavoriteProduct();
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("product", product);
                v.getContext().startActivity(intent);
            });
            binding.btnFavoriteRemove.setOnClickListener(v -> {
                Product product = binding.getFavoriteProduct();
                viewModel.removeFavoriteProduct(product);
            });
        }
    }
}
