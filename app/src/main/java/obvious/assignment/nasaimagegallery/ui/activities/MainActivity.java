package obvious.assignment.nasaimagegallery.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import obvious.assignment.nasaimagegallery.R;
import obvious.assignment.nasaimagegallery.data.model.ImageDetails;
import obvious.assignment.nasaimagegallery.data.viewmodel.ImageViewModel;
import obvious.assignment.nasaimagegallery.databinding.ActivityMainBinding;
import obvious.assignment.nasaimagegallery.ui.adapters.ImageListAdapter;

public class MainActivity extends AppCompatActivity {

    private List<ImageDetails> mImageList;
    private ImageViewModel mViewModel;

    //ui elements
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //initializing the list
        mImageList = new ArrayList<>();

        //initializing view model
        mViewModel = new ViewModelProvider(this).get(ImageViewModel.class);

        mViewModel.getAllImageList().observe(this, new Observer<List<ImageDetails>>() {
            @Override
            public void onChanged(List<ImageDetails> imageDetails) {
                //check if list is empty
                if(imageDetails == null || imageDetails.size() == 0) {
                    //fetch at least 10 images from server
                    mViewModel.fetchAndSaveImages();
                } else if(!mViewModel.isDataLoaded()) {
                    //user has fetched the data already we will check for latest image
                    mViewModel.fetchTodayImage();

                }

                mImageList.addAll(imageDetails);
                populateData();
            }
        });

    }


    private void populateData() {
        ImageListAdapter adapter = new ImageListAdapter(mImageList);
        mBinding.setAdapter(adapter);
    }
}
