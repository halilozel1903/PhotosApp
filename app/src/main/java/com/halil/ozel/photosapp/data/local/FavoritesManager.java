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
        if (photo == null || photo.getId() == null) {
            return;
        }
        
        List<FavoritePhoto> favorites = getFavorites();

        // Remove if already exists
        favorites.removeIf(f -> f != null && f.getId() != null && f.getId().equals(photo.getId()));

        favorites.add(0, photo);
        saveFavorites(favorites);
    }

    public void removeFavorite(String photoId) {
        if (photoId == null) {
            return;
        }
        
        List<FavoritePhoto> favorites = getFavorites();
        favorites.removeIf(f -> f != null && f.getId() != null && f.getId().equals(photoId));
        saveFavorites(favorites);
    }

    public boolean isFavorite(String photoId) {
        if (photoId == null) {
            return false;
        }
        
        List<FavoritePhoto> favorites = getFavorites();
        return favorites.stream().anyMatch(f -> f != null && f.getId() != null && f.getId().equals(photoId));
    }

    public List<FavoritePhoto> getFavorites() {
        try {
            String json = sharedPreferences.getString(KEY_FAVORITES, null);
            if (json == null || json.isEmpty()) {
                return new ArrayList<>();
            }

            Type type = new TypeToken<ArrayList<FavoritePhoto>>() {}.getType();
            List<FavoritePhoto> favorites = gson.fromJson(json, type);
            return favorites != null ? favorites : new ArrayList<>();
        } catch (Exception e) {
            // Return empty list if any error occurs during deserialization
            return new ArrayList<>();
        }
    }

    private void saveFavorites(List<FavoritePhoto> favorites) {
        if (favorites == null) {
            return;
        }
        
        try {
            String json = gson.toJson(favorites);
            sharedPreferences.edit().putString(KEY_FAVORITES, json).apply();
        } catch (Exception e) {
            // Log error but don't crash
        }
    }

    public void clearFavorites() {
        sharedPreferences.edit().remove(KEY_FAVORITES).apply();
    }
}

