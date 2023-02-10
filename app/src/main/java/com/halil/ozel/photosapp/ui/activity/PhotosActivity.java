package com.halil.ozel.photosapp.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.halil.ozel.photosapp.R;
import com.halil.ozel.photosapp.ui.adapter.PhotosAdapter;
import com.halil.ozel.photosapp.api.FlickrApi;
import com.halil.ozel.photosapp.api.FlickrService;
import com.halil.ozel.photosapp.data.Photo;
import com.halil.ozel.photosapp.data.ResponsePhotos;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotosActivity extends AppCompatActivity {
    PhotosAdapter photosAdapter;
    RecyclerView rvPhotos;
    ProgressBar pbPhoto;
    GridLayoutManager gridLayoutManager;
    FlickrService flickrService;

    Boolean isLoading = true;
    int pastVisibleItems, visibleItemCount, totalItemCount, previousTotal = 0;
    int viewThreshold = 20;
    int pageNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        pbPhoto = findViewById(R.id.pbPhoto);
        rvPhotos = findViewById(R.id.rvPhotos);

        gridLayoutManager = new GridLayoutManager(this, 2);
        rvPhotos.setHasFixedSize(true);
        rvPhotos.setLayoutManager(gridLayoutManager);

        flickrService = FlickrApi.getRetrofitInstance().create(FlickrService.class);
        pbPhoto.setVisibility(View.VISIBLE);

        Call<ResponsePhotos> call = flickrService.getResponse(20, pageNumber);
        call.enqueue(new Callback<ResponsePhotos>() {
            @Override
            public void onResponse(Call<ResponsePhotos> call, Response<ResponsePhotos> response) {
                List<Photo> photoList;
                if (response.body() != null) {
                    photoList = response.body().getPhotos().getPhoto();
                    photosAdapter = new PhotosAdapter(photoList, getApplicationContext());
                    rvPhotos.setAdapter(photosAdapter);
                    pbPhoto.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponsePhotos> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });


        // scrolling and new photos upload
        rvPhotos.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

                    if (!isLoading && (totalItemCount - visibleItemCount) <= (pastVisibleItems + viewThreshold)) {
                        pageNumber++;
                        performPagination();
                        isLoading = true;
                    }
                }
            }

        });
    }


    // Pagination function and service call
    private void performPagination() {
        pbPhoto.setVisibility(View.VISIBLE);

        Call<ResponsePhotos> call = flickrService.getResponse(20, pageNumber);
        call.enqueue(new Callback<ResponsePhotos>() {
            @Override
            public void onResponse(Call<ResponsePhotos> call, Response<ResponsePhotos> response) {
                List<Photo> photoList;
                if (response.body() != null) {
                    photoList = response.body().getPhotos().getPhoto();
                    photosAdapter.uploadImages(photoList);
                    pbPhoto.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ResponsePhotos> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
