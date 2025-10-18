package com.halil.ozel.photosapp.di;

import com.halil.ozel.photosapp.api.FlickrService;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;

import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class ApiModule {

    @Provides
    @Singleton
    public FlickrService provideFlickrService(Retrofit retrofit) {
        return retrofit.create(FlickrService.class);
    }
}

