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
import com.group05.emarket.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private final Context _context;
    private final List<Category> _categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        this._context = context;
        this._categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(_context);
        View heroView = inflater.inflate(R.layout.recycler_item_category, parent, false);
        return new ViewHolder(heroView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = _categories.get(position);
        holder._ivCategory.setImageResource(category.getImage());
        holder._tvName.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return _categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView _ivCategory;
        private final TextView _tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            _ivCategory = itemView.findViewById(R.id.iv_category);
            _tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
