package obvious.assignment.nasaimagegallery.data.viewmodel;

import android.app.Application;
import android.text.TextUtils;

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
        //if user first time opening the app current date will empty fetch from pref.
        //we will fetch 10 items then save the last fetch date to pref.
        //if user scroll for more we will fetch data from the last fetched date which we saved to next 10.
        String dateTillFetched = mRepository.getDateTillDataFetched();
        if(TextUtils.isEmpty(dateTillFetched)) {
            dateTillFetched = mRepository.getCurrentDate();
        }
        String dateArr[] = mRepository.getDateListForFetching(dateTillFetched);
        if(dateArr != null) {
            for(String date : dateArr) {
                mRepository.fetchAndSaveImages(date);
            }
            mRepository.setDateTillDataFetched(dateArr[0]);
        }
    }

    //fetching image for current date
    public void fetchTodayImage() {
        mRepository.fetchAndSaveImages(mRepository.getCurrentDate());
    }

    //checking weather user already opened the app and data is already present
    //we only need to fetch current image.
    public boolean isDataLoaded() {
        String currentDate = mRepository.getCurrentDate();
        String savedCurrDate = mRepository.getSavedCurrDate();
        if(currentDate.equalsIgnoreCase(savedCurrDate)) {
            return true;
        } else {
            return false;
        }
     }

}
