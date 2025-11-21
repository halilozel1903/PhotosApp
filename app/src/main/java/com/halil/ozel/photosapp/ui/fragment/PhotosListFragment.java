package com.halil.ozel.photosapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.halil.ozel.photosapp.R;
import com.halil.ozel.photosapp.databinding.FragmentPhotosListBinding;
import com.halil.ozel.photosapp.ui.adapter.PhotosAdapter;
import com.halil.ozel.photosapp.utils.Constants;
import com.halil.ozel.photosapp.viewmodel.PhotoViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PhotosListFragment extends Fragment {
    private FragmentPhotosListBinding binding;
    private PhotosAdapter photosAdapter;
    private PhotoViewModel viewModel;
    private GridLayoutManager gridLayoutManager;

    private boolean isLoading = false;
    private int pastVisibleItems, visibleItemCount, totalItemCount, previousTotal = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPhotosListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupMenu();
        setupRecyclerView();
        setupViewModel();
        setupScrollListener();
        setupSwipeRefresh();
    }

    private void setupMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.action_favorites) {
                    Navigation.findNavController(binding.getRoot())
                            .navigate(R.id.action_photosListFragment_to_favoritesFragment);
                    return true;
                } else if (itemId == R.id.action_search) {
                    Toast.makeText(requireContext(), "Search feature coming soon!", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (itemId == R.id.action_about) {
                    showAboutDialog();
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void setupRecyclerView() {
        if (getContext() == null || binding == null) {
            return;
        }
        
        gridLayoutManager = new GridLayoutManager(requireContext(), Constants.GRID_COLUMNS);
        binding.rvPhotos.setHasFixedSize(true);
        binding.rvPhotos.setLayoutManager(gridLayoutManager);

        photosAdapter = new PhotosAdapter(requireContext(), photoUrl -> {
            if (photoUrl != null && binding != null) {
                // Navigate to detail fragment
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXTRA_POSTER_URL, photoUrl);
                Navigation.findNavController(binding.getRoot())
                        .navigate(R.id.action_photosListFragment_to_photoDetailFragment, bundle);
            }
        });
        binding.rvPhotos.setAdapter(photosAdapter);
    }

    private void setupViewModel() {
        if (getActivity() == null) {
            return;
        }
        
        viewModel = new ViewModelProvider(this).get(PhotoViewModel.class);

        // Observe photos
        viewModel.getPhotos().observe(getViewLifecycleOwner(), photos -> {
            if (binding == null) return;
            
            if (photos != null && !photos.isEmpty()) {
                photosAdapter.submitList(photos);
                binding.emptyView.setVisibility(View.GONE);
            } else {
                binding.emptyView.setVisibility(View.VISIBLE);
            }
        });

        // Observe loading state
        viewModel.getLoading().observe(getViewLifecycleOwner(), loading -> {
            if (binding == null) return;
            
            if (loading != null) {
                binding.pbPhoto.setVisibility(loading ? View.VISIBLE : View.GONE);
                binding.swipeRefresh.setRefreshing(loading);
                isLoading = loading;
            }
        });

        // Observe errors
        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (binding == null || getContext() == null) return;
            
            if (error != null && !error.isEmpty()) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupScrollListener() {
        if (binding == null) return;
        
        binding.rvPhotos.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (gridLayoutManager == null || viewModel == null) return;

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

    private void setupSwipeRefresh() {
        if (binding == null || viewModel == null) return;
        
        binding.swipeRefresh.setOnRefreshListener(() -> {
            previousTotal = 0;
            viewModel.refresh();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showAboutDialog() {
        if (getContext() == null) return;

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_about, null);
        builder.setView(dialogView);
        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}
