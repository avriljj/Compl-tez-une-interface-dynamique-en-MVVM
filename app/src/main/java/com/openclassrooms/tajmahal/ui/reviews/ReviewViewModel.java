package com.openclassrooms.myrepo.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.tajmahal.data.repository.ReviewRepository;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.List;


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
}
