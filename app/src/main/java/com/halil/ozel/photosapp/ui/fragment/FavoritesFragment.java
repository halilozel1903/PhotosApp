package com.halil.ozel.photosapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.halil.ozel.photosapp.R;
import com.halil.ozel.photosapp.data.local.FavoritePhoto;
import com.halil.ozel.photosapp.data.local.FavoritesManager;
import com.halil.ozel.photosapp.databinding.FragmentFavoritesBinding;
import com.halil.ozel.photosapp.ui.adapter.FavoritesAdapter;
import com.halil.ozel.photosapp.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavoritesFragment extends Fragment {
    private FragmentFavoritesBinding binding;
    private FavoritesAdapter adapter;

    @Inject
    FavoritesManager favoritesManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        loadFavorites();
    }

    private void setupRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), Constants.GRID_COLUMNS);
        binding.rvFavorites.setLayoutManager(layoutManager);

        adapter = new FavoritesAdapter(requireContext(), photoUrl -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.EXTRA_POSTER_URL, photoUrl);
            Navigation.findNavController(binding.getRoot())
                    .navigate(R.id.action_favoritesFragment_to_photoDetailFragment, bundle);
        }, photoId -> {
            favoritesManager.removeFavorite(photoId);
            loadFavorites();
        });

        binding.rvFavorites.setAdapter(adapter);
    }

    private void loadFavorites() {
        List<FavoritePhoto> favorites = favoritesManager.getFavorites();
        adapter.submitList(favorites);

        if (favorites.isEmpty()) {
            binding.emptyView.setVisibility(View.VISIBLE);
            binding.rvFavorites.setVisibility(View.GONE);
        } else {
            binding.emptyView.setVisibility(View.GONE);
            binding.rvFavorites.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavorites();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

