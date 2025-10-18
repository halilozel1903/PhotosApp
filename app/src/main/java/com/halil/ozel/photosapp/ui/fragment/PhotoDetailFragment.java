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
import androidx.navigation.Navigation;

import com.halil.ozel.photosapp.R;
import com.halil.ozel.photosapp.data.local.FavoritePhoto;
import com.halil.ozel.photosapp.data.local.FavoritesManager;
import com.halil.ozel.photosapp.databinding.FragmentPhotoDetailBinding;
import com.halil.ozel.photosapp.utils.Constants;
import com.halil.ozel.photosapp.utils.ImageLoader;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PhotoDetailFragment extends Fragment {
    private FragmentPhotoDetailBinding binding;
    private String posterUrl;
    private String photoId;

    @Inject
    FavoritesManager favoritesManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPhotoDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            posterUrl = getArguments().getString(Constants.EXTRA_POSTER_URL);
            photoId = getArguments().getString("photoId", posterUrl); // Use URL as fallback ID
            ImageLoader.loadDetailImage(binding.ivPhotoDetail, posterUrl);
        }

        setupMenu();

        binding.btnBack.setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp()
        );

        binding.btnFavorite.setOnClickListener(v -> toggleFavorite());

        updateFavoriteButton();
    }

    private void setupMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_photo_detail, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.action_share) {
                    sharePhoto();
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void toggleFavorite() {
        if (favoritesManager.isFavorite(photoId)) {
            favoritesManager.removeFavorite(photoId);
            Toast.makeText(requireContext(), R.string.removed_from_favorites, Toast.LENGTH_SHORT).show();
        } else {
            FavoritePhoto favorite = new FavoritePhoto(photoId, posterUrl, "Photo " + photoId);
            favoritesManager.addFavorite(favorite);
            Toast.makeText(requireContext(), R.string.added_to_favorites, Toast.LENGTH_SHORT).show();
        }
        updateFavoriteButton();
    }

    private void updateFavoriteButton() {
        if (favoritesManager.isFavorite(photoId)) {
            binding.btnFavorite.setImageResource(android.R.drawable.star_big_on);
        } else {
            binding.btnFavorite.setImageResource(android.R.drawable.star_big_off);
        }
    }

    private void sharePhoto() {
        // Share functionality can be implemented here
        Toast.makeText(requireContext(), "Share feature coming soon!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
