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

public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.ViewHolder> {
    private final Context context;
    private final List<Order> orders;

    public HistoryItemAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_transaction_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        var orderName = order.getName();
        var orderDate = order.getUpdated_at();
        var orderStatus = order.getStatus();
        var orderAmount = order.getTotalPrice();

        switch (orderStatus) {
            case PENDING:
                holder._ivOrder.setImageResource(R.drawable.ic_pending);
                break;
            case DELIVERING:
                holder._ivOrder.setImageResource(R.drawable.ic_delivering);
                break;
            case DELIVERED:
                holder._ivOrder.setImageResource(R.drawable.ic_delivered);
                break;
            case CANCELLED:
                holder._ivOrder.setImageResource(R.drawable.ic_order_cancelled);
                break;
        }

        holder._tvTitle.setText(orderName);
        holder._tvDate.setText(orderDate);
        holder._tvAmount.setText(String.format("$ %s", orderAmount));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView _ivOrder;

        private final TextView _tvTitle;

        private final TextView _tvDate;

        private final TextView _tvAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            _ivOrder = itemView.findViewById(R.id.iv_history_image);
            _tvTitle = itemView.findViewById(R.id.tv_history_title);
            _tvDate = itemView.findViewById(R.id.tv_history_date);
            _tvAmount = itemView.findViewById(R.id.tv_history_amount);
        }
    }
}
