package com.openclassrooms.tajmahal;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.openclassrooms.tajmahal.domain.model.Review;
import com.openclassrooms.tajmahal.ui.reviews.ReviewViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

public class ReviewViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private ReviewViewModel viewModel;

    @Mock
    private Observer<List<Review>> observer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        viewModel = new ReviewViewModel();
        viewModel.getReviews().observeForever(observer); // Observe LiveData
    }

    @Test
    public void addReview_shouldAddReviewToList() {
        // Given a new review
        Review review = new Review("John Doe", "https://example.com/image.jpg", "Great place!", 5);

        // When the review is added
        boolean result = viewModel.addReview(review);

        // Then the review list should be updated
        List<Review> expectedReviews = new ArrayList<>();
        expectedReviews.add(review);

        assertTrue(result); // Check that the review was added
        assertEquals(expectedReviews, viewModel.getReviews().getValue());
    }

    @Test
    public void addReview_shouldNotAddReviewWithEmptyComment() {
        // Given a new review with an empty comment
        Review review = new Review("John Doe", "https://example.com/image.jpg", "", 5);

        // When the review is added
        boolean result = viewModel.addReview(review);

        // Then the review list should not be updated
        assertFalse(result); // Check that the review was not added
        verify(observer, never()).onChanged(viewModel.getReviews().getValue());
    }

    @Test
    public void addReview_shouldNotAddReviewWithZeroRating() {
        // Given a new review with a zero star rating
        Review review = new Review("John Doe", "https://example.com/image.jpg", "Nice place", 0);

        // When the review is added
        boolean result = viewModel.addReview(review);

        // Then the review list should not be updated
        assertFalse(result); // Check that the review was not added
        verify(observer, never()).onChanged(viewModel.getReviews().getValue());
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
