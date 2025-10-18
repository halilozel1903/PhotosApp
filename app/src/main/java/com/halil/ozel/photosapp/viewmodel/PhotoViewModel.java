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
        loadingLiveData.setValue(true);

        repository.getPhotos(Constants.ITEMS_PER_PAGE, page).observeForever(photos -> {
            loadingLiveData.setValue(false);
            if (photos != null) {
                allPhotos.addAll(photos);
                photosLiveData.setValue(new ArrayList<>(allPhotos));
            } else {
                errorLiveData.setValue("Failed to load photos");
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
