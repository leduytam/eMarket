package com.group05.emarket.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group05.emarket.R;
import com.group05.emarket.databinding.ListItemSortingBinding;
import com.group05.emarket.enums.SortProductOption;

import java.util.List;

public class SortProductOptionAdapter extends RecyclerView.Adapter<SortProductOptionAdapter.ViewHolder> {
    private int selectedIndex = 0;
    private final List<SortProductOption> options;
    private final Context context;

    public SortProductOptionAdapter(Context context, List<SortProductOption> options) {
        this.options = options;
        this.context = context;
    }

    public void setSelectedIndex(int selectedIndex) {

        this.selectedIndex = selectedIndex;
        notifyDataSetChanged();
    }

    public void setSelectedOption(SortProductOption option) {
        int index = options.indexOf(option);
        
        if (index != -1) {
            setSelectedIndex(index);
        }
    }

    public SortProductOption getSelectedOption() {
        return options.get(selectedIndex);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemSortingBinding binding = ListItemSortingBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        var selectedOption = options.get(position);
        holder._tvOption.setText(SortProductOption.toString(selectedOption));

        if (position == selectedIndex) {
            holder._ivTick.setVisibility(View.VISIBLE);
        } else {
            holder._ivTick.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(v -> {
            int index = holder.getAdapterPosition();

            if (index != selectedIndex) {
                selectedIndex = index;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView _tvOption;
        private final ImageView _ivTick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            _tvOption = itemView.findViewById(R.id.tv_option);
            _ivTick = itemView.findViewById(R.id.iv_tick);
        }
    }
}