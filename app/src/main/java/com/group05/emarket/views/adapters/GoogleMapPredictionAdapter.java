package com.group05.emarket.views.adapters;

import android.content.Context;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group05.emarket.R;
import com.group05.emarket.models.Review;
import com.group05.emarket.utilities.TimeAgo;

import java.util.List;

public class GoogleMapPredictionAdapter extends RecyclerView.Adapter<GoogleMapPredictionAdapter.ViewHolder> {
    private final Context context;
    private final List<Address> addresses;

    public GoogleMapPredictionAdapter(Context context, List<Address> reviews) {
        this.context = context;
        this.addresses = reviews;
    }

    @NonNull
    @Override
    public GoogleMapPredictionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_google_map_prediction, parent, false);
        return new GoogleMapPredictionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoogleMapPredictionAdapter.ViewHolder holder, int position) {
        Address review = addresses.get(position);
        holder.tvPrediction.setText(review.getAddressLine(0));
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvPrediction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPrediction = itemView.findViewById(R.id.tv_prediction);
        }
    }
}
