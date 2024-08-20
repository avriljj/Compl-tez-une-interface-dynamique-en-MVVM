package com.openclassrooms.tajmahal;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.openclassrooms.tajmahal.domain.model.Review;
import com.openclassrooms.tajmahal.ui.reviews.ReviewViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ReviewViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private ReviewViewModel viewModel;

    @Mock
    private Observer<List<Review>> observer;

    @Before
    public void setUp() {
       // Observe LiveData
        viewModel = new ReviewViewModel();
        observer = mock(Observer.class);
        viewModel.getReviews().observeForever(observer);
    }

    @Test
    public void addReview_shouldAddReviewToList() {
        // Given a new review
        Review review = new Review("John Doe", "https://example.com/image.jpg", "Great place!", 5);

        // When the review is added
        boolean result = viewModel.addReview(review);

        // Then the review list should be updated
        List<Review> expectedReviews = viewModel.getReviews().getValue();
        expectedReviews.add(review);

        assertTrue(result); // Check that the review was added
        assertEquals(expectedReviews, viewModel.getReviews().getValue());
    }

    @Test
    public void testAddReview_InvalidReview() {
        // Given
        Review invalidReview = new Review("User4", "https://image4.jpg", "", 0);

        // When
        boolean result = viewModel.addReview(invalidReview);

        // Then
        assertEquals(false, result);
        assertEquals(5, viewModel.getReviews().getValue().size());
    }



    @Test
    public void addReview_shouldNotAddReviewWithEmptyComment() {
        // Given
        Review invalidReview = new Review("User5", "https://image5.jpg", "", 4); // Empty comment, valid rating

        // When
        boolean result = viewModel.addReview(invalidReview);

        // Then
        assertEquals(false, result); // Ensure the review is not added
        assertEquals(5, viewModel.getReviews().getValue().size()); // The list should remain empty
    }

    @Test
    public void addReview_shouldNotAddReviewWithEmptyRating() {
        // Given
        Review invalidReview = new Review("User5", "https://image5.jpg", "some review here", 0 ); // Empty comment, valid rating

        // When
        boolean result = viewModel.addReview(invalidReview);

        // Then
        assertEquals(false, result); // Ensure the review is not added
        assertEquals(5, viewModel.getReviews().getValue().size()); // The list should remain empty
    }

    @Test
    public void addReview_shouldNotifyObserver() {
        // Given a new review
        Review review = new Review("Jane Doe", "https://example.com/image.jpg", "Nice food!", 4);

        // When the review is added
        boolean result = viewModel.addReview(review);

        // Then the observer should be notified
        assertTrue(result); // Check that the review was added
        verify(observer).onChanged(viewModel.getReviews().getValue());
    }
}
