package com.group05.emarket.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group05.emarket.R;
import com.group05.emarket.models.Product;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private final Context _context;
    private final List<Product> _products;
    private NumberFormat _currencyFormatter;

    public ProductAdapter(Context context, List<Product> products) {
        this._context = context;
        this._products = products;
        this._currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(_context);
        View view = inflater.inflate(R.layout.recycler_item_product, parent, false);
        return new ProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = _products.get(position);
        holder._tvName.setText(product.getName());
        holder._ivImage.setImageResource(product.getImage());
        holder._tvPrice.setText(_currencyFormatter.format(product.getPrice()));
        holder._tvRatingCount.setText(String.format("%s Reviews", product.getRatingCount()));
        holder._tvRating.setText(String.valueOf(product.getRating()));
    }

    @Override
    public int getItemCount() {
        return _products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView _ivImage;
        private final TextView _tvName;
        private final TextView _tvPrice;
        private final TextView _tvRatingCount;
        private final TextView _tvRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            _ivImage = itemView.findViewById(R.id.iv_image);
            _tvName = itemView.findViewById(R.id.tv_name);
            _tvPrice = itemView.findViewById(R.id.tv_price);
            _tvRatingCount = itemView.findViewById(R.id.tv_rating_count);
            _tvRating = itemView.findViewById(R.id.tv_rating);
        }
    }
}
