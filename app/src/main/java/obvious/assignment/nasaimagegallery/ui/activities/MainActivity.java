package obvious.assignment.nasaimagegallery.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

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
import obvious.assignment.nasaimagegallery.utility.RecyclerViewClickListener;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickListener {

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

                mImageList = imageDetails;
                mBinding.progressBar.setVisibility(View.GONE);
                populateData();
            }
        });

    }


    private void populateData() {
        ImageListAdapter adapter = new ImageListAdapter(mImageList, this);
        mBinding.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(int pos) {
        //Toast.makeText(this, imageDetails.getDate(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("SELECTED_ITEM", pos);
        startActivity(intent);
    }
}
