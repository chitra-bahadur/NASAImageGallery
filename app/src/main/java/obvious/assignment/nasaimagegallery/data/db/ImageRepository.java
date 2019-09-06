package obvious.assignment.nasaimagegallery.data.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import obvious.assignment.nasaimagegallery.data.model.ImageDetails;
import obvious.assignment.nasaimagegallery.data.rest.ApiClient;
import obvious.assignment.nasaimagegallery.data.rest.ImageApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageRepository {
    private ImageDetailsDao mImageDao;
    private LiveData<List<ImageDetails>> mAllImageList;
    private ImageApi mImageApi;

    public ImageRepository(Application application) {
        ImageDetailsDb db = ImageDetailsDb.getDatabase(application);
        mImageDao = db.imageDetailsDao();
        mAllImageList = mImageDao.getAllImages();
        mImageApi = ApiClient.getClient().create(ImageApi.class);
    }

    /*public ImageRepository() {
        mImageApi = ApiClient.getClient().create(ImageApi.class);
    }*/

    public void fetchAndSaveImages(String date) {
        //final MutableLiveData<ImageDetails> newData = new MutableLiveData<>();
        mImageApi.getApodImage(date).enqueue(new Callback<ImageDetails>() {
            @Override
            public void onResponse(Call<ImageDetails> call, Response<ImageDetails> response) {
                if(response.isSuccessful()) {
                    //System.out.println(response.body());
                    ImageDetails details = response.body();
                    insert(details);
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
            long id = imageDao.insert(imageDetails[0]);
            return null;
        }
    }
}
