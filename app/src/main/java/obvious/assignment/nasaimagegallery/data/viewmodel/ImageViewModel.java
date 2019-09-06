package obvious.assignment.nasaimagegallery.data.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import obvious.assignment.nasaimagegallery.data.db.ImageRepository;
import obvious.assignment.nasaimagegallery.data.model.ImageDetails;
import obvious.assignment.nasaimagegallery.utility.SharePrefUtil;
import obvious.assignment.nasaimagegallery.utility.Util;

public class ImageViewModel extends AndroidViewModel {

    private ImageRepository mRepository;
    private LiveData<List<ImageDetails>> mAllImageList;
    private SharePrefUtil mPrefUtil;
    private Util mUtil;

    ///pref constants used for fetching and saving data from shared preference
    private final String PREF_NAME = "NASA";
    private final String DATE_TILL_DATA_FETCHED = "DATE_TILL_DATA_FETCHED";
    private final String CURRENT_DATE = "CURRENT_DATE";

    public ImageViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ImageRepository(application);
        mPrefUtil = new SharePrefUtil(application.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE));
        mUtil = new Util();
    }

    public LiveData<List<ImageDetails>> getAllImageList() {
        mAllImageList = mRepository.getAllImageList();
        return mAllImageList;
    }

    //fetch current date
    public String getCurrentDate() {
        return mUtil.getDate();
    }

    //fetched image for current date
    public void fetchAndSaveImages() {
        mRepository.fetchAndSaveImages(getCurrentDate());
        mPrefUtil.put(CURRENT_DATE, getCurrentDate());
    }

    //fetch old image
    public void fetchAndSaveOldImages() {
        int dateTillLastFetched = mPrefUtil.getInteger(DATE_TILL_DATA_FETCHED);
    }

}
