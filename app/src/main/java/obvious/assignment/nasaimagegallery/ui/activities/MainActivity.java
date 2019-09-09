package obvious.assignment.nasaimagegallery.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import obvious.assignment.nasaimagegallery.R;
import obvious.assignment.nasaimagegallery.data.model.ImageDetails;
import obvious.assignment.nasaimagegallery.data.viewmodel.ImageViewModel;
import obvious.assignment.nasaimagegallery.databinding.ActivityMainBinding;
import obvious.assignment.nasaimagegallery.ui.adapters.ImageListAdapter;
import obvious.assignment.nasaimagegallery.ui.listeners.RecyclerViewClickListener;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickListener {

    ImageListAdapter adapter;
    private List<ImageDetails> mImageList;
    private ImageViewModel mViewModel;

    /*@Inject
    ViewModelProvider.AndroidViewModelFactory viewModelFactory;*/
    //ui elements
    private ActivityMainBinding mBinding;
    //number of items in the dataset after the last load;
    private int mPreviousTotal = 0;
    //true if waiting for the last set to load
    private boolean mLoading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //initializing the list
        mImageList = new ArrayList<>();

        //initializing view model
        mViewModel = new ViewModelProvider(this).get(ImageViewModel.class);

        //adapter
        adapter = new ImageListAdapter(mImageList, this);

        mBinding.setAdapter(adapter);
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

                adapter.setImageDetailList(imageDetails);
                mBinding.progressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();

            }
        });

        initScrollListener();
    }

    private void initScrollListener() {
        mBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = recyclerView.getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int firstVisibleItem = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if(mLoading) {
                    if(totalItemCount > mPreviousTotal) {
                        mLoading = false;
                        mPreviousTotal = totalItemCount;
                    }
                }

                int visibleThreshold = 10;
                if(!mLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    //end has been reached
                    mViewModel.fetchAndSaveImages();
                    mLoading = true;
                }
            }
        });

        /*mBinding.recyclerView.addOnScrollListener(new EndlessRecyclerViewOnScrollListener() {
            @Override
            public void onLoadMore() {
                mViewModel.fetchAndSaveImages();
            }
        });*/
    }


    @Override
    public void onItemClicked(int pos) {
        //Toast.makeText(this, imageDetails.getDate(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("SELECTED_ITEM", pos);
        startActivity(intent);
    }
}
