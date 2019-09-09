package obvious.assignment.nasaimagegallery.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import obvious.assignment.nasaimagegallery.data.db.ImageRepository;
import obvious.assignment.nasaimagegallery.data.model.ImageDetails;

public class ImageViewModel extends AndroidViewModel {

    private ImageRepository mRepository;
    public LiveData<List<ImageDetails>> mAllImageList;

    public ImageViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ImageRepository(application);
    }

    public LiveData<List<ImageDetails>> getAllImageList() {
        mAllImageList = mRepository.getAllImageList();
        return mAllImageList;
    }

    public void fetchAndSaveImages() {
        mRepository.fetchAndSaveImages();
    }

    //fetching image for current date
    public void fetchTodayImage() {
        mRepository.fetchAndSaveImages(mRepository.getCurrentDate());
    }

    //checking weather user already opened the app and data is already present
    //we only need to fetch current image.
    public boolean isDataLoaded() {
        return mRepository.isDataLoaded();
     }

}
