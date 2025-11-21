package com.halil.ozel.photosapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.halil.ozel.photosapp.databinding.ItemPhotoBinding;
import com.halil.ozel.photosapp.repository.PhotoRepository;
import com.halil.ozel.photosapp.data.Photo;
import com.halil.ozel.photosapp.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosHolder> {

    private final Context context;
    private final PhotoRepository repository;
    private final OnPhotoClickListener listener;
    private List<Photo> photoList = new ArrayList<>();

    public interface OnPhotoClickListener {
        void onPhotoClick(String photoUrl);
    }

    // Constructor for use WITH repository injection
    public PhotosAdapter(Context context, PhotoRepository repository, OnPhotoClickListener listener) {
        this.context = context;
        this.repository = repository;
        this.listener = listener;
    }

    // Simplified constructor for Fragment usage (repository injected separately)
    public PhotosAdapter(Context context, OnPhotoClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.repository = null; // Repository will be provided per-item if needed
    }

    @NonNull
    @Override
    public PhotosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPhotoBinding binding = ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new PhotosHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final PhotosHolder holder, int position) {
        Photo photo = photoList.get(position);
        holder.bind(photo);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public void submitList(List<Photo> newPhotos) {
        if (newPhotos != null) {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new PhotoDiffCallback(this.photoList, newPhotos));
            this.photoList = new ArrayList<>(newPhotos);
            diffResult.dispatchUpdatesTo(this);
        }
    }

    public class PhotosHolder extends RecyclerView.ViewHolder {
        private final ItemPhotoBinding binding;

        public PhotosHolder(@NonNull ItemPhotoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Photo photo) {
            if (photo == null) {
                return;
            }

            // Build URL directly from photo data (more efficient than API call per item)
            String url = buildPhotoUrl(photo);

            if (url != null) {
                ImageLoader.loadImage(binding.ivPhoto, url);

                binding.getRoot().setOnClickListener(view -> {
                    if (listener != null) {
                        listener.onPhotoClick(url);
                    }
                });
            } else {
                // Set placeholder if URL cannot be built
                binding.ivPhoto.setImageResource(com.halil.ozel.photosapp.R.drawable.ic_launcher_background);
            }
        }

        private String buildPhotoUrl(Photo photo) {
            try {
                // Build URL directly from Photo object if it has all required data
                if (photo == null || photo.getId() == null || photo.getSecret() == null ||
                    photo.getServer() == null || photo.getFarm() == null) {
                    return null;
                }

                int farm = Integer.parseInt(photo.getFarm());
                String server = photo.getServer();
                String id = photo.getId();
                String secret = photo.getSecret();

                return "https://farm" + farm + ".staticflickr.com/" +
                       server + "/" + id + "_" + secret + ".jpg";
            } catch (NumberFormatException | NullPointerException e) {
                return null;
            }
        }
    }

    // DiffUtil for efficient RecyclerView updates
    private static class PhotoDiffCallback extends DiffUtil.Callback {
        private final List<Photo> oldList;
        private final List<Photo> newList;

        public PhotoDiffCallback(List<Photo> oldList, List<Photo> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            Photo oldPhoto = oldList.get(oldItemPosition);
            Photo newPhoto = newList.get(newItemPosition);
            if (oldPhoto == null || newPhoto == null || 
                oldPhoto.getId() == null || newPhoto.getId() == null) {
                return false;
            }
            return oldPhoto.getId().equals(newPhoto.getId());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            Photo oldPhoto = oldList.get(oldItemPosition);
            Photo newPhoto = newList.get(newItemPosition);
            if (oldPhoto == null || newPhoto == null) {
                return oldPhoto == newPhoto;
            }
            return oldPhoto.getId() != null && oldPhoto.getId().equals(newPhoto.getId()) &&
                   oldPhoto.getTitle() != null && oldPhoto.getTitle().equals(newPhoto.getTitle());
        }
    }
}
