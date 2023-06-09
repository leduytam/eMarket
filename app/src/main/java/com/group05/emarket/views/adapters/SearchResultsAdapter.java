package com.group05.emarket.views.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.group05.emarket.R;
import com.group05.emarket.models.Product;

import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {
    private final Context context;
    private List<Product> products;

    public SearchResultsAdapter(Context context, List<Product> categories) {
        this.context = context;
        this.products = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        View view = holder.itemView;
        holder.tvName.setText(product.getName());

        Glide.with(context)
                .load(product.getImage())
                .into(holder.ivProductImage);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(30);

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivProductImage;
        private final TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.iv_image);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }
}
