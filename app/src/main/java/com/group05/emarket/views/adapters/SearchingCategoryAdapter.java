package com.group05.emarket.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group05.emarket.R;
import com.group05.emarket.views.activities.ProductListCategoryActivity;
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
        holder._ivCategory.setImageResource(category.getImage());
        holder._tvName.setText(category.getName());
        // set radius for category
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(view.getResources().getColor(category.getBackground()));
        gd.setCornerRadius(30);

        holder._rlCategory.setBackground(gd);


        holder.itemView.setOnClickListener(v -> {
            Intent intentToCategory = new Intent(context, ProductListCategoryActivity.class);
            intentToCategory.putExtra("categoryId", category.getId());
            context.startActivity(intentToCategory);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView _ivCategory;
        private final TextView _tvName;
        private final RelativeLayout _rlCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            _ivCategory = itemView.findViewById(R.id.iv_category);
            _tvName = itemView.findViewById(R.id.tv_name);
            _rlCategory = itemView.findViewById(R.id.rl_category_container);
        }
    }
}
