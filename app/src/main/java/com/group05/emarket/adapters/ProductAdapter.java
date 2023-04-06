package com.group05.emarket.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group05.emarket.R;
import com.group05.emarket.activities.ProductDetailActivity;
import com.group05.emarket.models.Product;
import com.group05.emarket.utilities.Formatter;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private final List<Product> _products;
    private final Context _context;

    public ProductAdapter(Context context, List<Product> products) {
        _context = context;
        _products = products;
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
        holder._tvRatingCount.setText(String.format("%s Reviews", product.getRatingCount()));
        holder._tvRating.setText(String.valueOf(product.getAvgRating()));

        if (product.getDiscount() > 0) {
            holder._tvDiscount.setText(String.format("%s%%", product.getDiscount()));
            holder._tvDiscount.setVisibility(View.VISIBLE);

            double discountPrice = product.getPrice() * (1 - product.getDiscount() / 100.0);
            holder._tvPrice.setText(Formatter.formatCurrency(discountPrice));
        } else {
            holder._tvDiscount.setVisibility(View.INVISIBLE);
            holder._tvPrice.setText(Formatter.formatCurrency(product.getPrice()));
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(_context, ProductDetailActivity.class);
            intent.putExtra("id", product.getId());
            _context.startActivity(intent);
        });
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
        private final TextView _tvDiscount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            _ivImage = itemView.findViewById(R.id.iv_image);
            _tvName = itemView.findViewById(R.id.tv_name);
            _tvPrice = itemView.findViewById(R.id.tv_price);
            _tvRatingCount = itemView.findViewById(R.id.tv_rating_count);
            _tvRating = itemView.findViewById(R.id.tv_rating);
            _tvDiscount = itemView.findViewById(R.id.tv_discount);
        }
    }

    public static class ProductItemDecoration extends RecyclerView.ItemDecoration {
        private final int _spanCount;
        private final int _spacing;
        private final boolean _isIncludeEdge;

        public ProductItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this._spanCount = spanCount;
            this._spacing = spacing;
            this._isIncludeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % _spanCount;

            if (_isIncludeEdge) {
                outRect.left = _spacing - column * _spacing / _spanCount;
                outRect.right = (column + 1) * _spacing / _spanCount;

                if (position < _spanCount) {
                    outRect.top = _spacing;
                }
                outRect.bottom = _spacing;
            } else {
                outRect.left = column * _spacing / _spanCount;
                outRect.right = _spacing - (column + 1) * _spacing / _spanCount;
                if (position >= _spanCount) {
                    outRect.top = _spacing;
                }
            }
        }
    }
}
