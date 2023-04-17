package com.group05.emarket.views.adapters;

import android.content.Context;
import android.content.Intent;
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
        var orderImage = order.getProducts().get(0).getProduct().getImage();
        var orderName = "Order #" + order.getId();
        holder._ivOrder.setImageResource(orderImage);
        holder._tvName.setText(orderName);
        holder._tvPrice.setText(String.format("$ %s", order.getTotalPrice()));
        holder._tvAddress.setText(order.getAddress());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView _ivOrder;
        private final TextView _tvName;

        private final TextView _tvPrice;
        private final TextView _tvAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            _ivOrder = itemView.findViewById(R.id.iv_order_thumbnail);
            _tvName = itemView.findViewById(R.id.tv_order_name);
            _tvPrice = itemView.findViewById(R.id.tv_order_price);
            _tvAddress = itemView.findViewById(R.id.tv_order_address);
        }
    }
}
