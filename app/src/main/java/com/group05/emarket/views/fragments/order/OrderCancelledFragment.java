package com.group05.emarket.views.fragments.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group05.emarket.MockData;
import com.group05.emarket.R;
import com.group05.emarket.models.Order;
import com.group05.emarket.views.adapters.OrderItemAdapter;

import java.util.List;

public class OrderCancelledFragment extends Fragment {
    public OrderCancelledFragment() {
    }

    public static OrderCancelledFragment newInstance() {
        return new OrderCancelledFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_orders_list, container, false);

        List<Order> pendingOrders;
        pendingOrders = MockData.getOrders(Order.OrderStatus.CANCELLED);
        RecyclerView recyclerOrdersView = layout.findViewById(R.id.ll_orders_container).findViewById(R.id.rv_pending_orders);
        recyclerOrdersView.setAdapter(new OrderItemAdapter(getContext(), pendingOrders));
        recyclerOrdersView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerOrdersView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        if (!pendingOrders.isEmpty()) {
            layout.findViewById(R.id.ll_empty_orders_container).setVisibility(View.GONE);
            layout.findViewById(R.id.ll_orders_container).setVisibility(View.VISIBLE);
        } else {
            layout.findViewById(R.id.ll_empty_orders_container).setVisibility(View.VISIBLE);
            layout.findViewById(R.id.ll_orders_container).setVisibility(View.GONE);
        }
        return layout;
    }
}
