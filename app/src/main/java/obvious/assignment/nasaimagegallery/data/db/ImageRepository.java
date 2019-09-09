package obvious.assignment.nasaimagegallery.data.db;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import obvious.assignment.nasaimagegallery.data.model.ImageDetails;
import obvious.assignment.nasaimagegallery.data.rest.ApiClient;
import obvious.assignment.nasaimagegallery.data.rest.ImageApi;
import obvious.assignment.nasaimagegallery.utility.SharePrefUtil;
import obvious.assignment.nasaimagegallery.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageRepository {
    private ImageDetailsDao mImageDao;
    private LiveData<List<ImageDetails>> mAllImageList;
    private ImageApi mImageApi;

    private SharePrefUtil mPrefUtil;
    ///pref constants used for fetching and saving data from shared preference
    private final String PREF_NAME = "NASA";
    private final String DATE_TILL_DATA_FETCHED = "DATE_TILL_DATA_FETCHED";
    private final String CURRENT_DATE = "CURRENT_DATE";


    private Util mUtil;

    public ImageRepository(Application application) {
        ImageDetailsDb db = ImageDetailsDb.getDatabase(application);
        mImageDao = db.imageDetailsDao();
        mAllImageList = mImageDao.getAllImages();
        mImageApi = ApiClient.getClient().create(ImageApi.class);
        mPrefUtil = new SharePrefUtil(application.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE));
        mUtil = new Util();
    }

    //fetch current date
    public String getCurrentDate() {
        return mUtil.getDate();
    }

    public void fetchAndSaveImages() {
        //if user first time opening the app current date will empty fetch from pref.
        //we will fetch 10 items then save the last fetch date to pref.
        //if user scroll for more we will fetch data from the last fetched date which we saved to next 10.
        String dateTillFetched = getDateTillDataFetched();
        if(TextUtils.isEmpty(dateTillFetched)) {
            dateTillFetched = getCurrentDate();
        }
        String dateArr[] = getDateListForFetching(dateTillFetched);
        if(dateArr != null) {
            for(String date : dateArr) {
                fetchAndSaveImages(date);
            }
            setDateTillDataFetched(dateArr[0]);
        }
    }

    public void fetchAndSaveImages(final String date) {
        //final MutableLiveData<ImageDetails> newData = new MutableLiveData<>();
        mImageApi.getApodImage(date).enqueue(new Callback<ImageDetails>() {
            @Override
            public void onResponse(Call<ImageDetails> call, Response<ImageDetails> response) {
                if(response.isSuccessful()) {

                    ImageDetails details = response.body();
                    //System.out.println(date);
                    System.out.println(details.getDate() + "|" + details.getUrl());
                    insert(details);
                    mPrefUtil.put(CURRENT_DATE, getCurrentDate());
                } else {
                    Log.e("ImageRepository", "" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ImageDetails> call, Throwable t) {
                //newData.setValue(null);
                t.printStackTrace();
            }
        });
        //return newData;
    }

    public LiveData<List<ImageDetails>> getAllImageList() {
        return mAllImageList;
    }

    public void insert(ImageDetails imageDetails) {
        new InsertAsyncTask(mImageDao).execute(imageDetails);
    }

    private static class InsertAsyncTask extends AsyncTask<ImageDetails, Void, Void> {

        private ImageDetailsDao imageDao;

        InsertAsyncTask(ImageDetailsDao imageDao) {
            this.imageDao = imageDao;
        }

        @Override
        protected Void doInBackground(ImageDetails... imageDetails) {
            imageDao.insert(imageDetails[0]);
            return null;
        }
    }

    public void setDateTillDataFetched(String date) {
        mPrefUtil.put(DATE_TILL_DATA_FETCHED, date);
    }

    public String getDateTillDataFetched() {
        return mPrefUtil.getString(DATE_TILL_DATA_FETCHED);
    }

    public String[] getDateListForFetching(String dateTillDataFetched) {
        return mUtil.getDateListForFetching(dateTillDataFetched);
    }

    public String getSavedCurrDate() {
        return mPrefUtil.getString(CURRENT_DATE);
    }

    //checking weather user already opened the app and data is already present
    //we only need to fetch current image.
    public boolean isDataLoaded() {
        String currentDate = getCurrentDate();
        String savedCurrDate = getSavedCurrDate();
        if(currentDate.equalsIgnoreCase(savedCurrDate)) {
            return true;
        } else {
            return false;
        }
    }
}
