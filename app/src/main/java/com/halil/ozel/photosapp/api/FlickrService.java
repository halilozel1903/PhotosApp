package com.halil.ozel.photosapp.api;

import com.halil.ozel.photosapp.data.ResponsePhoto;
import com.halil.ozel.photosapp.data.ResponsePhotos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrService {

    @GET("?method=flickr.photos.getRecent&api_key=5adbb887ee60c548c02690ef16b848cd" +
            "&format=json&nojsoncallback=1")
    Call<ResponsePhotos> getResponse(
            @Query("per_page") int per_page,
            @Query("page") int page

    );


    @GET("?method=flickr.photos.getInfo" +
            "&api_key=5adbb887ee60c548c02690ef16b848cd" + "" +
            "&format=json&nojsoncallback=1")
    Call<ResponsePhoto> getPhotoInfo(
            @Query("photo_id") String photo_id
    );
}
