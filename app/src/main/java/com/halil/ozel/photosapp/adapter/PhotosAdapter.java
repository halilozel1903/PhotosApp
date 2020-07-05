package com.halil.ozel.photosapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.halil.ozel.photosapp.R;
import com.halil.ozel.photosapp.api.FlickrApi;
import com.halil.ozel.photosapp.api.FlickrService;
import com.halil.ozel.photosapp.data.Photo;
import com.halil.ozel.photosapp.data.ResponsePhoto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosHolder> {

    private List<Photo> photoList;
    private Context context;


    public PhotosAdapter(List<Photo> photoList, Context context) {
        this.photoList = photoList;
        this.context = context;
    }

    @NonNull
    @Override
    public PhotosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new PhotosHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final PhotosHolder holder, int position) {

        Photo photo = photoList.get(position);


        FlickrService flickrService = FlickrApi.getRetrofitInstance().create(FlickrService.class);

        Call<ResponsePhoto> call = flickrService.getPhotoInfo(photo.getId());

        call.enqueue(new Callback<ResponsePhoto>() {
            @Override
            public void onResponse(Call<ResponsePhoto> call, Response<ResponsePhoto> response) {


                if (response.body() != null) {


                   //https://farm66.staticflickr.com/65535/50079652836_4095c95086.jpg

                     String id = response.body().getPhoto().getId();
                     String secret = response.body().getPhoto().getSecret();
                     String server = response.body().getPhoto().getServer();
                     int farm = response.body().getPhoto().getFarm();

                    Glide.with(context).load("https://farm"+farm+".staticflickr.com/"+server+"/"+id+"_"+secret+".jpg").into(holder.ivPhoto);


                }


            }

            @Override
            public void onFailure(Call<ResponsePhoto> call, Throwable t) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class PhotosHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;


        public PhotosHolder(View view) {
            super(view);

            ivPhoto = view.findViewById(R.id.ivPhoto);
        }
    }
}
