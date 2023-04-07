package com.group05.emarket.views.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.group05.emarket.R;
import com.group05.emarket.databinding.ListItemCartBinding;
import com.group05.emarket.models.CartItem;
import com.group05.emarket.utilities.Formatter;

public class CartListAdapter extends ListAdapter<CartItem, CartListAdapter.ViewHolder> {
    private final OnCartChangedListener listener;
    private final Context context;

    public CartListAdapter(Context context, OnCartChangedListener listener) {
        super(CartItem.itemCallback);
        this.context = context;
        this.listener = listener;
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
        holder.binding.setCartItem(getItem(position));
        holder.binding.executePendingBindings();
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

            binding.btnDecreaseQuantity.setOnClickListener(v -> {
                CartItem item = binding.getCartItem();
                listener.onCartItemQuantityChanged(item, item.getQuantity() - 1);
            });

            binding.btnIncreaseQuantity.setOnClickListener(v -> {
                CartItem item = binding.getCartItem();
                listener.onCartItemQuantityChanged(item, item.getQuantity() + 1);
            });
        }
    }

    public interface OnCartChangedListener {
        void onCartItemDeleted(CartItem item);

        void onCartItemQuantityChanged(CartItem item, int count);
    }

    public static class SwipeToDeleteOrderItemCallback extends ItemTouchHelper.SimpleCallback {
        private CartListAdapter adapter;
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
                adapter.listener.onCartItemDeleted(adapter.getItem(viewHolder.getAdapterPosition()));
            }
        }
    }
}
