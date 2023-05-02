package com.group05.emarket.views.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group05.emarket.R;
import com.group05.emarket.databinding.ListItemOrderBinding;
import com.group05.emarket.models.Order;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {
    private final Context context;
    private final List<Order> orders;

    public OrderItemAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemOrderBinding binding = ListItemOrderBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.binding.setOrder(order);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ListItemOrderBinding binding;

        public ViewHolder(@NonNull ListItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
