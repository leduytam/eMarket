package com.group05.emarket.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.group05.emarket.R;
import com.group05.emarket.views.activities.ProductDetailActivity;
import com.group05.emarket.models.Product;
import com.group05.emarket.utilities.Formatter;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private final List<Product> products;
    private final Context context;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    public void setProducts(List<Product> products) {
        this.products.clear();
        this.products.addAll(products);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_item_product, parent, false);
        return new ProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.tvName.setText(product.getName());

        Glide.with(context).load(product.getImage()).into(holder.ivImage);

        holder.tvRatingCount.setText(String.format("%s Reviews", product.getRatingCount()));
        holder.tvRating.setText(String.valueOf(product.getAvgRating()));

        if (product.getDiscount() > 0) {
            holder.tvDiscount.setText(String.format("%s%%", product.getDiscount()));
            holder.tvDiscount.setVisibility(View.VISIBLE);

            double discountPrice = product.getPrice() * (1 - product.getDiscount() / 100.0);
            holder.tvPrice.setText(Formatter.formatCurrency(discountPrice));
            holder.tvOldPrice.setText(Formatter.formatCurrency(product.getPrice()));
        } else {
            holder.tvPrice.setText(Formatter.formatCurrency(product.getPrice()));
        }

        holder.rlDiscount.setVisibility(product.getDiscount() > 0 ? View.VISIBLE : View.GONE);
        holder.tvOldPrice.setVisibility(product.getDiscount() > 0 ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("product", product);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivImage;
        private final TextView tvName;
        private final TextView tvPrice;
        private final TextView tvOldPrice;
        private final TextView tvRatingCount;
        private final TextView tvRating;
        private final TextView tvDiscount;
        private final RelativeLayout rlDiscount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.iv_image);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvOldPrice = itemView.findViewById(R.id.tv_old_price);
            tvOldPrice.setPaintFlags(tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvRatingCount = itemView.findViewById(R.id.tv_rating_count);
            tvRating = itemView.findViewById(R.id.tv_rating);
            tvDiscount = itemView.findViewById(R.id.tv_discount);
            rlDiscount = itemView.findViewById(R.id.rl_discount);
        }
    }
}
