package com.group05.emarket.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
import com.group05.emarket.views.activities.CategoryActivity;
import com.group05.emarket.models.Category;

import java.util.List;

public class SearchingCategoryAdapter extends RecyclerView.Adapter<SearchingCategoryAdapter.ViewHolder> {
    private final Context context;
    private final List<Category> categories;

    public SearchingCategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycle_item_search_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);
        View view = holder.itemView;

        Glide.with(context)
                .load(category.getImage())
                .into(holder.ivCategory);

        holder.tvName.setText(category.getName());
        // set radius for category
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor(category.getBackgroundColor()));
        gd.setCornerRadius(30);

        holder.rlCategory.setBackground(gd);

        holder.itemView.setOnClickListener(v -> {
            Intent intentToCategory = new Intent(context, CategoryActivity.class);
            intentToCategory.putExtra("category", category);
            context.startActivity(intentToCategory);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivCategory;
        private final TextView tvName;
        private final RelativeLayout rlCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCategory = itemView.findViewById(R.id.iv_category);
            tvName = itemView.findViewById(R.id.tv_name);
            rlCategory = itemView.findViewById(R.id.rl_category_container);
        }
    }
}
