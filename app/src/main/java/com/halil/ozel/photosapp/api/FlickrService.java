package com.halil.ozel.photosapp.api;

import com.halil.ozel.photosapp.data.ResponsePhoto;
import com.halil.ozel.photosapp.data.ResponsePhotos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrService {


    @GET("?method=flickr.photos.getRecent&api_key=90d35997e8f0f3686e8aaef1260a3008" +
            "&per_page=20&page=1&format=json&nojsoncallback=1")
    Call<ResponsePhotos> getResponse();




    @GET("?method=flickr.photos.getInfo" +
            "&api_key=90d35997e8f0f3686e8aaef1260a3008"+"" +
            "&format=json&nojsoncallback=1")
    Call<ResponsePhoto> getPhotoInfo(

            @Query("photo_id") String photo_id
    );
}
