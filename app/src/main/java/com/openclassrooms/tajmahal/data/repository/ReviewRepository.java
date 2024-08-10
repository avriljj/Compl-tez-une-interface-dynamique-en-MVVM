package com.openclassrooms.tajmahal.data.repository;

import com.openclassrooms.tajmahal.data.service.RestaurantFakeApi;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.List;

/**
 * Repository class for managing review data.
 */
public class ReviewRepository {

    /**
     * Retrieves a list of reviews.
     * This should be replaced with actual data fetching logic (e.g., from a database or API).
     *
     * @return A list of reviews.
     */
    public List<Review> getReviews() {
        // Replace with actual implementation, such as fetching from a database or network.
        return new RestaurantFakeApi().getReviews(); // Use your fake API or actual data source
    }
}
