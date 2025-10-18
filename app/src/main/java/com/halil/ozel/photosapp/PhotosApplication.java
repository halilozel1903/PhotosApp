package com.halil.ozel.photosapp;

import android.app.Application;
import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class PhotosApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}

