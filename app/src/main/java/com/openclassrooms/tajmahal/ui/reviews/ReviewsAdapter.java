package com.openclassrooms.tajmahal.ui.reviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.domain.model.Review;

import com.bumptech.glide.Glide;

public class ReviewsAdapter extends ListAdapter<Review, ReviewsAdapter.ReviewViewHolder> {

    // Constructor
    public ReviewsAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = getItem(position);
        holder.textView.setText(review.getComment());
        holder.userNameTextView.setText(review.getUsername());
        // Load review user avatar using Glide or another image loading library
        Glide.with(holder.itemView.getContext())
                .load(review.getPicture())
                .circleCrop()// Assuming review.getPicture() returns a URL
                .into(holder.avatarView);
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView avatarView;
        TextView userNameTextView;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.commentTextView);
            avatarView = itemView.findViewById(R.id.avatarView);
            userNameTextView = itemView.findViewById(R.id.userInList);
        }
    }

    // DiffUtil callback to handle list updates efficiently
    private static final DiffUtil.ItemCallback<Review> DIFF_CALLBACK = new DiffUtil.ItemCallback<Review>() {


        @Override
        public boolean areItemsTheSame(@NonNull Review oldItem, @NonNull Review newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Review oldItem, @NonNull Review newItem) {
            return oldItem.equals(newItem); // Ensure equals() is properly implemented in Review
        }
    };
}
