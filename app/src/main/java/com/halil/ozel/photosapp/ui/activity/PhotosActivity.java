package com.halil.ozel.photosapp.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.halil.ozel.photosapp.databinding.ActivityPhotosBinding;
import com.halil.ozel.photosapp.ui.adapter.PhotosAdapter;
import com.halil.ozel.photosapp.utils.Constants;
import com.halil.ozel.photosapp.viewmodel.PhotoViewModel;

public class PhotosActivity extends AppCompatActivity implements PhotosAdapter.OnPhotoClickListener {
    private ActivityPhotosBinding binding;
    private PhotosAdapter photosAdapter;
    private PhotoViewModel viewModel;
    private GridLayoutManager gridLayoutManager;

    private boolean isLoading = false;
    private int pastVisibleItems, visibleItemCount, totalItemCount, previousTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhotosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupRecyclerView();
        setupViewModel();
        setupScrollListener();
    }

    private void setupRecyclerView() {
        gridLayoutManager = new GridLayoutManager(this, Constants.GRID_COLUMNS);
        binding.rvPhotos.setHasFixedSize(true);
        binding.rvPhotos.setLayoutManager(gridLayoutManager);

        photosAdapter = new PhotosAdapter(this, this);
        binding.rvPhotos.setAdapter(photosAdapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(PhotoViewModel.class);

        // Observe photos
        viewModel.getPhotos().observe(this, photos -> {
            if (photos != null && !photos.isEmpty()) {
                photosAdapter.submitList(photos);
            }
        });

        // Observe loading state
        viewModel.getLoading().observe(this, loading -> {
            if (loading != null) {
                binding.pbPhoto.setVisibility(loading ? View.VISIBLE : View.GONE);
                isLoading = loading;
            }
        });

        // Observe errors
        viewModel.getError().observe(this, error -> {
            if (error != null) {
                // You can show a toast or snackbar here
            }
        });
    }

    private void setupScrollListener() {
        binding.rvPhotos.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = gridLayoutManager.getChildCount();
                totalItemCount = gridLayoutManager.getItemCount();
                pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();

                if (dy > 0) {
                    if (isLoading) {
                        if (totalItemCount > previousTotal) {
                            isLoading = false;
                            previousTotal = totalItemCount;
                        }
                    }

                    if (!isLoading && (totalItemCount - visibleItemCount) <= (pastVisibleItems + Constants.VIEW_THRESHOLD)) {
                        viewModel.loadNextPage();
                        isLoading = true;
                    }
                }
            }
        });
    }

    @Override
    public void onPhotoClick(String photoUrl) {
        // Handle photo click - you can open a detail activity or dialog here
        // For example:
        // Intent intent = new Intent(this, PhotoDetailActivity.class);
        // intent.putExtra("photo_url", photoUrl);
        // startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
