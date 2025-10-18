package com.halil.ozel.photosapp.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class FavoritesManager {
    private static final String PREFS_NAME = "favorites_prefs";
    private static final String KEY_FAVORITES = "favorites";

    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    @Inject
    public FavoritesManager(@ApplicationContext Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    public void addFavorite(FavoritePhoto photo) {
        List<FavoritePhoto> favorites = getFavorites();

        // Remove if already exists
        favorites.removeIf(f -> f.getId().equals(photo.getId()));

        favorites.add(0, photo);
        saveFavorites(favorites);
    }

    public void removeFavorite(String photoId) {
        List<FavoritePhoto> favorites = getFavorites();
        favorites.removeIf(f -> f.getId().equals(photoId));
        saveFavorites(favorites);
    }

    public boolean isFavorite(String photoId) {
        List<FavoritePhoto> favorites = getFavorites();
        return favorites.stream().anyMatch(f -> f.getId().equals(photoId));
    }

    public List<FavoritePhoto> getFavorites() {
        String json = sharedPreferences.getString(KEY_FAVORITES, null);
        if (json == null) {
            return new ArrayList<>();
        }

        Type type = new TypeToken<ArrayList<FavoritePhoto>>() {}.getType();
        return gson.fromJson(json, type);
    }

    private void saveFavorites(List<FavoritePhoto> favorites) {
        String json = gson.toJson(favorites);
        sharedPreferences.edit().putString(KEY_FAVORITES, json).apply();
    }

    public void clearFavorites() {
        sharedPreferences.edit().remove(KEY_FAVORITES).apply();
    }
}

