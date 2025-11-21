package com.halil.ozel.photosapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.halil.ozel.photosapp.data.Photo;
import com.halil.ozel.photosapp.repository.PhotoRepository;
import com.halil.ozel.photosapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class PhotoViewModel extends ViewModel {
    private final PhotoRepository repository;
    private final MutableLiveData<List<Photo>> photosLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    private final List<Photo> allPhotos = new ArrayList<>();
    private int currentPage = 1;

    @Inject
    public PhotoViewModel(PhotoRepository repository) {
        this.repository = repository;
        loadPhotos(currentPage);
    }

    public void loadPhotos(int page) {
        if (loadingLiveData.getValue() != null && loadingLiveData.getValue()) {
            // Already loading, prevent duplicate requests
            return;
        }
        
        loadingLiveData.setValue(true);

        repository.getPhotos(Constants.ITEMS_PER_PAGE, page).observeForever(new androidx.lifecycle.Observer<List<Photo>>() {
            @Override
            public void onChanged(List<Photo> photos) {
                loadingLiveData.setValue(false);
                if (photos != null && !photos.isEmpty()) {
                    allPhotos.addAll(photos);
                    photosLiveData.setValue(new ArrayList<>(allPhotos));
                } else if (photos == null) {
                    errorLiveData.setValue("Failed to load photos. Please check your connection.");
                } else {
                    errorLiveData.setValue("No more photos available.");
                }
                // Remove observer to prevent memory leaks
                repository.getPhotos(Constants.ITEMS_PER_PAGE, page).removeObserver(this);
            }
        });
    }

    public void loadNextPage() {
        currentPage++;
        loadPhotos(currentPage);
    }

    public void refresh() {
        allPhotos.clear();
        currentPage = 1;
        loadPhotos(currentPage);
    }

    public LiveData<List<Photo>> getPhotos() {
        return photosLiveData;
    }

    public LiveData<Boolean> getLoading() {
        return loadingLiveData;
    }

    public LiveData<String> getError() {
        return errorLiveData;
    }
}
