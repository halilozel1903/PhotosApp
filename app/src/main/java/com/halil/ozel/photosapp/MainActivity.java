package com.halil.ozel.photosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.halil.ozel.photosapp.adapter.PhotosAdapter;
import com.halil.ozel.photosapp.api.FlickrApi;
import com.halil.ozel.photosapp.api.FlickrService;
import com.halil.ozel.photosapp.data.Photo;
import com.halil.ozel.photosapp.data.ResponsePhoto;
import com.halil.ozel.photosapp.data.ResponsePhotos;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    PhotosAdapter photosAdapter;
    RecyclerView rvPhotos;
    ProgressBar pbPhoto;
    GridLayoutManager gridLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pbPhoto = findViewById(R.id.pbPhoto);

        rvPhotos = findViewById(R.id.rvPhotos);


        gridLayoutManager = new GridLayoutManager(this,2);
        rvPhotos.setHasFixedSize(true);
        rvPhotos.setLayoutManager(gridLayoutManager);


        FlickrService flickrService =
                FlickrApi.getRetrofitInstance().create(FlickrService.class);


        Call<ResponsePhotos> call = flickrService.getResponse();
        call.enqueue(new Callback<ResponsePhotos>() {
            @Override
            public void onResponse(Call<ResponsePhotos> call, Response<ResponsePhotos> response) {

                List<Photo>  photoList = response.body().getPhotos().getPhoto();

                photosAdapter = new PhotosAdapter(photoList,getApplicationContext());

                rvPhotos.setAdapter(photosAdapter);


            }

            @Override
            public void onFailure(Call<ResponsePhotos> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

}
