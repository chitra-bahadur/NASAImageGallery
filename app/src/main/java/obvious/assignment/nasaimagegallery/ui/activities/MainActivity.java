package obvious.assignment.nasaimagegallery.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import obvious.assignment.nasaimagegallery.R;
import obvious.assignment.nasaimagegallery.data.model.ImageDetails;
import obvious.assignment.nasaimagegallery.data.viewmodel.ImageViewModel;

public class MainActivity extends AppCompatActivity {

    private List<ImageDetails> mImageList;
    private ImageViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing the list
        mImageList = new ArrayList<>();

        //initializing view model
        mViewModel = new ViewModelProvider(this).get(ImageViewModel.class);

        mViewModel.getAllImageList().observe(this, new Observer<List<ImageDetails>>() {
            @Override
            public void onChanged(List<ImageDetails> imageDetails) {
                //check if image are present in local
                if(imageDetails != null && imageDetails.size() > 0) {
                    mViewModel.fetchAndSaveImages();
                } else {
                    //load images
                    //if app is opening first time we need to fetch data for at least 10 dates
                    mViewModel.fetchAndSaveImages();
                }

                mImageList.addAll(imageDetails);
            }
        });
    }
}
