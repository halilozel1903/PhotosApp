package com.halil.ozel.photosapp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.halil.ozel.photosapp.databinding.ActivityPhotosDetailBinding;
import com.halil.ozel.photosapp.utils.Constants;
import com.halil.ozel.photosapp.utils.ImageLoader;

public class PhotosDetailActivity extends AppCompatActivity {
    private ActivityPhotosDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhotosDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String posterUrl = getIntent().getStringExtra(Constants.EXTRA_POSTER_URL);
        ImageLoader.loadDetailImage(binding.ivPhotoDetail, posterUrl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
