package com.halil.ozel.photosapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.halil.ozel.photosapp.R;
import com.halil.ozel.photosapp.data.local.FavoritePhoto;
import com.halil.ozel.photosapp.databinding.ItemFavoritePhotoBinding;
import com.halil.ozel.photosapp.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder> {

    private final Context context;
    private final OnFavoriteClickListener clickListener;
    private final OnRemoveFavoriteListener removeListener;
    private List<FavoritePhoto> favoritesList = new ArrayList<>();

    public interface OnFavoriteClickListener {
        void onFavoriteClick(String photoUrl);
    }

    public interface OnRemoveFavoriteListener {
        void onRemoveFavorite(String photoId);
    }

    public FavoritesAdapter(Context context, OnFavoriteClickListener clickListener, OnRemoveFavoriteListener removeListener) {
        this.context = context;
        this.clickListener = clickListener;
        this.removeListener = removeListener;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFavoritePhotoBinding binding = ItemFavoritePhotoBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new FavoriteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        FavoritePhoto photo = favoritesList.get(position);
        holder.bind(photo);
    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }

    public void submitList(List<FavoritePhoto> favorites) {
        if (favorites != null) {
            this.favoritesList = new ArrayList<>(favorites);
        } else {
            this.favoritesList = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        private final ItemFavoritePhotoBinding binding;

        public FavoriteViewHolder(@NonNull ItemFavoritePhotoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(FavoritePhoto photo) {
            if (photo == null) {
                return;
            }
            
            ImageLoader.loadImage(binding.ivPhoto, photo.getUrl());
            
            String title = photo.getTitle();
            binding.tvTitle.setText(title != null ? title : "");

            binding.getRoot().setOnClickListener(v -> {
                if (clickListener != null && photo.getUrl() != null) {
                    clickListener.onFavoriteClick(photo.getUrl());
                }
            });

            binding.btnRemove.setOnClickListener(v -> {
                if (removeListener != null && photo.getId() != null) {
                    removeListener.onRemoveFavorite(photo.getId());
                }
            });
        }
    }
}

