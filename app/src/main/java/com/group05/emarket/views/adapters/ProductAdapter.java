package com.group05.emarket.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder._tvName.setText(product.getName());
        holder._ivImage.setImageResource(product.getImage());
        holder._tvRatingCount.setText(String.format("%s Reviews", product.getRatingCount()));
        holder._tvRating.setText(String.valueOf(product.getAvgRating()));

        if (product.getDiscount() > 0) {
            holder._tvDiscount.setText(String.format("%s%%", product.getDiscount()));
            holder._tvDiscount.setVisibility(View.VISIBLE);

            double discountPrice = product.getPrice() * (1 - product.getDiscount() / 100.0);
            holder._tvPrice.setText(Formatter.formatCurrency(discountPrice));
            holder._tvOldPrice.setText(Formatter.formatCurrency(product.getPrice()));
        } else {
            holder._tvPrice.setText(Formatter.formatCurrency(product.getPrice()));
        }

        holder._rlDiscount.setVisibility(product.getDiscount() > 0 ? View.VISIBLE : View.GONE);
        holder._tvOldPrice.setVisibility(product.getDiscount() > 0 ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("id", product.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView _ivImage;
        private final TextView _tvName;
        private final TextView _tvPrice;
        private final TextView _tvOldPrice;
        private final TextView _tvRatingCount;
        private final TextView _tvRating;
        private final TextView _tvDiscount;
        private final RelativeLayout _rlDiscount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            _ivImage = itemView.findViewById(R.id.iv_image);
            _tvName = itemView.findViewById(R.id.tv_name);
            _tvPrice = itemView.findViewById(R.id.tv_price);
            _tvOldPrice = itemView.findViewById(R.id.tv_old_price);
            _tvOldPrice.setPaintFlags(_tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            _tvRatingCount = itemView.findViewById(R.id.tv_rating_count);
            _tvRating = itemView.findViewById(R.id.tv_rating);
            _tvDiscount = itemView.findViewById(R.id.tv_discount);
            _rlDiscount = itemView.findViewById(R.id.rl_discount);
        }
    }
}
