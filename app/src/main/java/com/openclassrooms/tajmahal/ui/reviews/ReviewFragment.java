package com.openclassrooms.tajmahal.ui.reviews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import com.openclassrooms.tajmahal.ui.reviews.ReviewViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.data.service.RestaurantFakeApi;
import com.openclassrooms.tajmahal.databinding.FragmentReviewBinding;
import com.openclassrooms.tajmahal.databinding.ReviewItemBinding;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewFragment extends Fragment {

    private ReviewViewModel viewModel;
    private FragmentReviewBinding binding;

    private float myRating = 0;
    private ReviewsAdapter adapter;
    private List<Review> reviewList;
    private List<Review> reviewList2;
    private RestaurantFakeApi fakeApi = new RestaurantFakeApi();

    public static ReviewFragment newInstance() {
        return new ReviewFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout using view binding
        binding = FragmentReviewBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up the Toolbar
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the previous screen
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack(); // Pop the back stack entry
                } else {
                    // Optionally handle the case where there's no fragment to go back to
                    // For example, you might want to exit the activity
                    getActivity().finish();
                }
            }
        });

        // Retrieve reviews from the fake API
        reviewList = fakeApi.getReviews();
        reviewList2 = new ArrayList<>();

        Log.d("error","fakeapi");
        Log.d("error",reviewList.toString() );

        // Set up the adapter
        adapter = new ReviewsAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        setupViewModel();
        setupRatingBar();

        binding.validateReviewButton.setOnClickListener(v -> saveNewReview());



    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(ReviewViewModel.class);
        viewModel.getReviews().observe(getViewLifecycleOwner(), this::updateReviewList);
    }

    private void updateReviewList(List<Review> reviews) {
        if (adapter != null) {
            adapter.submitList(reviews); // Assuming you're using a ListAdapter
        }
    }

    private void setupRatingBar(){
        binding.rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                myRating = v; // Store the selected rating
                Toast.makeText(getContext(), "Rating: " + myRating, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void saveNewReview() {
        // Retrieve user input
        String currentUser = binding.activeUser.getText().toString();
        String reviewText = binding.editText.getText().toString();
        int rating = Math.round(binding.rating.getRating());

        // Create a new Review object
        Review newReview = new Review(currentUser, "https://xsgames.co/randomusers/assets/avatars/male/71.jpg", reviewText, rating);

        // Get the current list of reviews
        List<Review> currentReviews = viewModel.getReviews().getValue();

        // Create a new mutable list if the current list is unmodifiable or null
        List<Review> updatedReviews;
        if (currentReviews == null) {
            updatedReviews = new ArrayList<>();
        } else {
            updatedReviews = new ArrayList<>(currentReviews); // Create a new mutable list
        }

        validateReviewData();
        if (validateReviewData()){
            // Add the new review to the mutable list
            updatedReviews.add(newReview);

            // Update the LiveData with the new list
            viewModel.addReview(newReview);
        }

        // Log confirmation
        Log.d("review", "Review added: " + newReview);
        Log.d("review", "CurrentUser: " + currentUser);
        Log.d("review", "reviewText: " + reviewText);
        Log.d("review", "rating: " + rating);
        Log.d("review", "reviewList: " + reviewList);
        Log.d("review", "reviewList2: " + reviewList2);
    }

    private boolean validateReviewData() {
        String reviewText = binding.editText.getText().toString();
        float rating = binding.rating.getRating();

        if (reviewText.isEmpty()) {
            Toast.makeText(getContext(), "Review comment cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (rating == 0) {
            Toast.makeText(getContext(), "Please provide a star rating", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true; // Data is valid
    }


}
