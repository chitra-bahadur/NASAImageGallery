package obvious.assignment.nasaimagegallery.data.viewmodel;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

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
    public LiveData<List<ImageDetails>> mAllImageList;
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

    public void fetchAndSaveImages() {
        //if user first time opening the app current date will empty fetch from pref.
        //we will fetch 10 items then save the last fetch date to pref.
        //if user scroll for more we will fetch data from the last fetched date which we saved to next 10.
        String dateTillFetched = mPrefUtil.getString(DATE_TILL_DATA_FETCHED);
        if(TextUtils.isEmpty(dateTillFetched)) {
            dateTillFetched = getCurrentDate();
        }
        String dateArr[] = mUtil.getDateListForFetching(dateTillFetched);
        if(dateArr != null) {
            for(String date : dateArr) {
                mRepository.fetchAndSaveImages(date);
            }
            mPrefUtil.put(CURRENT_DATE, getCurrentDate());
            mPrefUtil.put(DATE_TILL_DATA_FETCHED, dateArr[0]);
        }
    }

    //fetching image for current date
    public void fetchTodayImage() {
        mRepository.fetchAndSaveImages(getCurrentDate());
    }

    //checking weather user already opened the app and data is already present
    //we only need to fetch current image.
    public boolean isDataLoaded() {
        if(getCurrentDate().equalsIgnoreCase(mPrefUtil.getString(CURRENT_DATE))) {
            return true;
        } else {
            return false;
        }
     }

}
