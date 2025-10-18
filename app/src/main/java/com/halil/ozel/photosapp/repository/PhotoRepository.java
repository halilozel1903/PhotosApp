package com.halil.ozel.photosapp.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.halil.ozel.photosapp.api.FlickrService;
import com.halil.ozel.photosapp.data.Photo;
import com.halil.ozel.photosapp.data.ResponsePhoto;
import com.halil.ozel.photosapp.data.ResponsePhotos;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class PhotoRepository {
    private static final String TAG = "PhotoRepository";
    private final FlickrService flickrService;

    @Inject
    public PhotoRepository(FlickrService flickrService) {
        this.flickrService = flickrService;
    }

    public LiveData<List<Photo>> getPhotos(int perPage, int page) {
        MutableLiveData<List<Photo>> photosLiveData = new MutableLiveData<>();

        Call<ResponsePhotos> call = flickrService.getResponse(perPage, page);
        call.enqueue(new Callback<ResponsePhotos>() {
            @Override
            public void onResponse(Call<ResponsePhotos> call, Response<ResponsePhotos> response) {
                if (response.body() != null && response.body().getPhotos() != null) {
                    photosLiveData.setValue(response.body().getPhotos().getPhoto());
                }
            }

            @Override
            public void onFailure(Call<ResponsePhotos> call, Throwable t) {
                Log.e(TAG, "Error loading photos: " + t.getMessage());
                photosLiveData.setValue(null);
            }
        });

        return photosLiveData;
    }

    public LiveData<String> getPhotoUrl(String photoId) {
        MutableLiveData<String> photoUrlLiveData = new MutableLiveData<>();

        Call<ResponsePhoto> call = flickrService.getPhotoInfo(photoId);
        call.enqueue(new Callback<ResponsePhoto>() {
            @Override
            public void onResponse(Call<ResponsePhoto> call, Response<ResponsePhoto> response) {
                if (response.body() != null && response.body().getPhoto() != null) {
                    String id = response.body().getPhoto().getId();
                    String secret = response.body().getPhoto().getSecret();
                    String server = response.body().getPhoto().getServer();
                    int farm = response.body().getPhoto().getFarm();

                    String url = "https://farm" + farm + ".staticflickr.com/" +
                                server + "/" + id + "_" + secret + ".jpg";
                    photoUrlLiveData.setValue(url);
                }
            }

            @Override
            public void onFailure(Call<ResponsePhoto> call, Throwable t) {
                Log.e(TAG, "Error loading photo info: " + t.getMessage());
                photoUrlLiveData.setValue(null);
            }
        });

        return photoUrlLiveData;
    }
}
