package com.group05.emarket.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group05.emarket.R;
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
        View view = inflater.inflate(R.layout.list_item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
//        var orderImage = order.getProducts().get(0).getProduct().getImage();
        var orderName = "Order #" + order.getId();
        holder.ivOrder.setImageResource(R.drawable.logo_hcmus);
        holder.tvName.setText(orderName);
        holder.tvPrice.setText(String.format("$ %s", order.getTotalPrice()));
        holder.tvAddress.setText(order.getAddress());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivOrder;
        private final TextView tvName;

        private final TextView tvPrice;
        private final TextView tvAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivOrder = itemView.findViewById(R.id.iv_order_thumbnail);
            tvName = itemView.findViewById(R.id.tv_order_name);
            tvPrice = itemView.findViewById(R.id.tv_order_price);
            tvAddress = itemView.findViewById(R.id.tv_order_address);
        }
    }
}
