package com.group05.emarket.adapters;

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
    private final Context _context;
    private final List<Review> _reviews;

    public ReviewAdapter(Context context, List<Review> reviews) {
        this._context = context;
        this._reviews = reviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(_context);
        View view = inflater.inflate(R.layout.recycler_item_review, parent, false);
        return new ReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = _reviews.get(position);
        holder._tvContent.setText(review.getContent());
        holder._rbRating.setRating(review.getRating());
        holder._tvCreatedAt.setText(TimeAgo.convert(review.getCreatedAt().toEpochSecond(ZoneOffset.UTC)));
        holder._tvReviewerName.setText(review.getReviewerName());
        holder._ivReviewerAvatar.setImageResource(review.getReviewerAvatar());
    }

    @Override
    public int getItemCount() {
        return _reviews.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView _tvContent;
        private final RatingBar _rbRating;
        private final TextView _tvCreatedAt;
        private final TextView _tvReviewerName;
        private final ImageView _ivReviewerAvatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            _tvContent = itemView.findViewById(R.id.tv_content);
            _rbRating = itemView.findViewById(R.id.rb_rating);
            _tvCreatedAt = itemView.findViewById(R.id.tv_date);
            _tvReviewerName = itemView.findViewById(R.id.tv_name);
            _ivReviewerAvatar = itemView.findViewById(R.id.iv_avatar);
        }
    }
}
