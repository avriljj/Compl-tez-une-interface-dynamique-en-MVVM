package com.openclassrooms.tajmahal.ui.reviews;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.tajmahal.data.repository.ReviewRepository;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.List;
import java.util.ArrayList;




/**
 * ViewModel responsible for managing data related to reviews.
 * It interacts with the repository to obtain reviews and exposes them via LiveData.
 */
public class ReviewViewModel extends ViewModel {

    private final ReviewRepository reviewRepository;
    private final MutableLiveData<List<Review>> reviewsLiveData;


    /**
     * Initializes the ViewModel by creating a repository and loading the reviews.
     */
    public ReviewViewModel() {
        reviewRepository = new ReviewRepository(); // Ensure this repository is set up for reviews
        reviewsLiveData = new MutableLiveData<>();
        loadReviews();
    }

    /**
     * Retrieves the reviews exposed via LiveData.
     *
     * @return An instance LiveData containing the current list of reviews.
     */
    public LiveData<List<Review>> getReviews() {
        return reviewsLiveData;
    }

    /**
     * Loads the reviews from the repository and publishes them via LiveData.
     * This method is called during the initialization of the ViewModel.
     */
    private void loadReviews() {
        List<Review> reviews = reviewRepository.getReviews(); // Ensure this method returns a list of reviews
        reviewsLiveData.postValue(reviews);
    }

    // Method to add a review
    public void addReview(Review review) {
        List<Review> currentReviews = reviewsLiveData.getValue();
        if (currentReviews == null) {
            currentReviews = new ArrayList<>();
        } else {
            currentReviews = new ArrayList<>(currentReviews); // Create a new mutable list
        }

        currentReviews.add(0,review);


        reviewsLiveData.setValue(currentReviews); // Update LiveData with the new list
    }

    public void validateData(){

    }

}
