package com.group05.emarket.views.fragments.order;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group05.emarket.MockData;
import com.group05.emarket.R;
import com.group05.emarket.databinding.FragmentOrdersListBinding;
import com.group05.emarket.models.Order;
import com.group05.emarket.viewmodels.OrderViewModel;
import com.group05.emarket.views.adapters.OrderItemAdapter;

import java.util.List;

public class OrderPendingFragment extends Fragment {
    private OrderViewModel orderViewModel;
    private FragmentOrdersListBinding binding;
    public OrderPendingFragment() {
    }

    public static OrderPendingFragment newInstance() {
        return new OrderPendingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrdersListBinding.inflate(inflater, container, false);
        orderViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new OrderViewModel.Factory(Order.OrderStatus.PENDING)).get(OrderViewModel.class);
        RecyclerView recyclerOrdersView = binding.llOrdersContainer.findViewById(R.id.rv_pending_orders);
        recyclerOrdersView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerOrdersView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        orderViewModel.getOrders().observe(getViewLifecycleOwner(), orders -> {
            recyclerOrdersView.setAdapter(new OrderItemAdapter(getContext(), orders));
            binding.llEmptyOrdersContainer.setVisibility(orders.isEmpty() ? View.VISIBLE : View.GONE);
            binding.llOrdersContainer.setVisibility(orders.isEmpty() ? View.GONE : View.VISIBLE );
        });
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        orderViewModel.fetch();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }
}
