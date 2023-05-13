package com.group05.emarket.views.adapters;

import android.content.Context;
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

import java.time.ZoneOffset;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private final Context context;
    private final List<Review> reviews;

    public ReviewAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_item_review, parent, false);
        return new ReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.tvContent.setText(review.getContent());
        holder.rbRating.setRating(review.getRating());
        holder.tvCreatedAt.setText(TimeAgo.convert(review.getCreatedAt().getTime()));
        holder.tvReviewerName.setText(review.getReviewer().getName());
        holder.ivReviewerAvatar.setImageResource(review.getReviewer().getAvatar());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvContent;
        private final RatingBar rbRating;
        private final TextView tvCreatedAt;
        private final TextView tvReviewerName;
        private final ImageView ivReviewerAvatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
            rbRating = itemView.findViewById(R.id.rb_rating);
            tvCreatedAt = itemView.findViewById(R.id.tv_created_at);
            tvReviewerName = itemView.findViewById(R.id.tv_name);
            ivReviewerAvatar = itemView.findViewById(R.id.iv_avatar);
        }
    }
}
