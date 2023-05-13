package com.group05.emarket.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.group05.emarket.R;
import com.group05.emarket.databinding.ListItemOrderBinding;
import com.group05.emarket.models.Order;
import com.group05.emarket.views.activities.OrderDetailActivity;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {
    private final Context context;
    private final List<Order> orders;
    private static Order currentOrder;

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
        currentOrder = order;
        holder.binding.setOrder(order);
        holder.binding.executePendingBindings();

        holder.binding.btnOrderDetail.setOnClickListener(v -> {
            Intent intent = new Intent(context, OrderDetailActivity.class);

            intent.putExtra("orderId", order.getId());
            intent.putExtra("orderStatus", order.getStatus().toString());
            intent.putExtra("userName", order.getName());
            intent.putExtra("userPhone", order.getPhone());
            intent.putExtra("userAddress", order.getAddress());
            intent.putExtra("totalPrice", order.getTotalPrice());
            intent.putExtra("isReviewed", order.getIsReviewed());

            context.startActivity(intent);
        });

        if (order != null) {
            var orderProducts = currentOrder.getOrderProducts();
            if (orderProducts.size() > 0) {
                var product = orderProducts.get(0).getProduct();
                Glide.with(holder.binding.getRoot())
                        .load(product.getImage())
                        .into(holder.binding.ivOrderThumbnail);
            }
        }
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
