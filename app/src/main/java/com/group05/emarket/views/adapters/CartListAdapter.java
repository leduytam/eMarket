package com.group05.emarket.views.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.group05.emarket.databinding.ListItemCartBinding;
import com.group05.emarket.models.CartItem;
import com.group05.emarket.utilities.Formatter;

import java.util.ArrayList;
import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    private final List<CartItem> cartItems;
    private final OnCartChangedListener listener;

    public CartListAdapter(OnCartChangedListener listener) {
        this.cartItems = new ArrayList<>();
        this.listener = listener;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems.clear();
        this.cartItems.addAll(cartItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemCartBinding binding = ListItemCartBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setCartItem(cartItems.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    @BindingAdapter("app:price")
    public static void formatPrice(TextView view, float price) {
        view.setText(Formatter.formatCurrency(price));
    }

    @BindingAdapter("app:imageSrc")
    public static void setImageResource(ImageView view, int resource) {
        view.setImageResource(resource);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ListItemCartBinding binding;

        public ViewHolder(@NonNull ListItemCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.tvOldPrice.setPaintFlags(binding.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            binding.btnDecreaseQuantity.setOnClickListener(v -> {
                CartItem item = binding.getCartItem();

                if (item.getQuantity() == 1) {
                    listener.onCartItemRemoved(item);
                    return;
                }

                listener.onCartItemQuantityChanged(item, item.getQuantity() - 1);
            });

            binding.btnIncreaseQuantity.setOnClickListener(v -> {
                CartItem item = binding.getCartItem();
                listener.onCartItemQuantityChanged(item, item.getQuantity() + 1);
            });
        }
    }

    public interface OnCartChangedListener {
        void onCartItemRemoved(CartItem item);

        void onCartItemQuantityChanged(CartItem item, int quantity);
    }

    public static class SwipeToDeleteOrderItemCallback extends ItemTouchHelper.SimpleCallback {
        private final CartListAdapter adapter;
        private final Paint p = new Paint();

        public SwipeToDeleteOrderItemCallback(CartListAdapter adapter) {
            super(0, ItemTouchHelper.LEFT);
            this.adapter = adapter;
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (direction == ItemTouchHelper.LEFT) {
                adapter.listener.onCartItemRemoved(adapter.cartItems.get(viewHolder.getAdapterPosition()));
            }
        }
    }
}
