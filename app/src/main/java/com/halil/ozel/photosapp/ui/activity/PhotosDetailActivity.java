package com.halil.ozel.photosapp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.halil.ozel.photosapp.R;

public class PhotosDetailActivity extends AppCompatActivity {


    ImageView ivPhotoDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_detail);


        ivPhotoDetail = findViewById(R.id.ivPhotoDetail);


        String posterUrl = getIntent().getStringExtra("posterUrl");


        Glide.with(this).load(posterUrl).into(ivPhotoDetail);
    }
}
