package com.group05.emarket.views.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.group05.emarket.databinding.RecycleOrderDetailBinding;
import com.group05.emarket.models.OrderProduct;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {
    private final List<OrderProduct> products;

    public OrderDetailAdapter(List<OrderProduct> products) {
        this.products = products;
    }
    @NonNull
    @Override
    public OrderDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecycleOrderDetailBinding binding = RecycleOrderDetailBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.ViewHolder holder, int position) {
        OrderProduct product = products.get(position);
        Log.d("OrderDetailAdapter", "onBindViewHolder: " + product.getProduct().getName());
        holder.binding.tvOrderName.setText(product.getProduct().getName());
        holder.binding.tvOrderDescription.setText(product.getProduct().getDescription());
        holder.binding.tvOrderPrice.setText(String.format("$ %.2f", product.getProduct().getDiscountedPrice()));
        holder.binding.tvOrderQuantity.setText(String.format("x%d", product.getQuantity()));
        Glide.with(holder.itemView)
                .load(product.getProduct().getImage())
                .into(holder.binding.ivProductImage);
        ;
    }

    @Override
    public int getItemCount() {
        Log.d("OrderDetailAdapter", "getItemCount: " + products.size());
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecycleOrderDetailBinding binding;

        public ViewHolder(@NonNull RecycleOrderDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
