package com.openclassrooms.tajmahal.ui.reviews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import com.openclassrooms.myrepo.ui.ReviewViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.data.service.RestaurantFakeApi;
import com.openclassrooms.tajmahal.databinding.FragmentReviewBinding;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.List;

public class ReviewFragment extends Fragment {

    private ReviewViewModel viewModel;
    private FragmentReviewBinding binding;
    private ReviewsAdapter adapter;
    private List<Review> reviewList;
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

        Log.d("error","fakeapi");
        Log.d("error",reviewList.toString() );

        // Set up the adapter
        adapter = new ReviewsAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        setupViewModel();
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
}
