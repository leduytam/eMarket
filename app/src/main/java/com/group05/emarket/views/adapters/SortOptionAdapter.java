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

import java.util.List;

public class SortOptionAdapter extends RecyclerView.Adapter<SortOptionAdapter.ViewHolder> {
    private int _selectedIndex = -1;
    private final List<String> _options;
    private final Context _context;

    public SortOptionAdapter(Context context, List<String> options) {
        _options = options;
        _context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(_context);
        View view = inflater.inflate(R.layout.list_item_sorting, parent, false);
        return new SortOptionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String option = _options.get(position);
        holder._tvOption.setText(option);

        if (position == _selectedIndex) {
            holder._ivTick.setVisibility(View.VISIBLE);
        } else {
            holder._ivTick.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(v -> {
            int index = holder.getAdapterPosition();

            if (index != _selectedIndex) {
                _selectedIndex = index;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return _options.size();
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


//package com.group05.emarket.adapters;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.group05.emarket.R;
//
//import java.util.List;
//
//public class SortOptionAdapter extends ArrayAdapter<String> {
//    private int _selectedIndex = -1;
//    private List<String> _options;
//
//    public SortOptionAdapter(Context context, List<String> options) {
//        super(context, R.layout.list_item_sorting, options);
//        _options = options;
//    }
//
//    public void setSelectedIndex(int index) {
//        _selectedIndex = index;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        ViewHolder viewHolder;
//
//        if (convertView == null) {
//            viewHolder = new ViewHolder();
//
//            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.list_item_sorting, parent, false);
//
//            viewHolder.tvOption = convertView.findViewById(R.id.tv_option);
//            viewHolder.ivTick = convertView.findViewById(R.id.iv_tick);
//
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//
//        viewHolder.tvOption.setText(_options.get(position));
//
//        if (_selectedIndex == position) {
//            viewHolder.ivTick.setVisibility(View.VISIBLE);
//        } else {
//            viewHolder.ivTick.setVisibility(View.INVISIBLE);
//        }
//
//        return convertView;
//
//    }
//
//    public static class ViewHolder {
//        public TextView tvOption;
//        public ImageView ivTick;
//    }
//}
