package obvious.assignment.nasaimagegallery.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;

import java.util.List;

import obvious.assignment.nasaimagegallery.R;
import obvious.assignment.nasaimagegallery.data.model.ImageDetails;
import obvious.assignment.nasaimagegallery.data.viewmodel.ImageViewModel;
import obvious.assignment.nasaimagegallery.databinding.ActivityDetailsBinding;
import obvious.assignment.nasaimagegallery.ui.adapters.ImageDetailsAdapter;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding mBinding;
    ImageViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        mViewModel = new ViewModelProvider(this).get(ImageViewModel.class);

        final int pos = getIntent().getIntExtra("SELECTED_ITEM", 0);
        mViewModel.getAllImageList().observe(this, new Observer<List<ImageDetails>>() {
            @Override
            public void onChanged(List<ImageDetails> imageDetailsList) {

                ImageDetailsAdapter adapter = new ImageDetailsAdapter(imageDetailsList);

                mBinding.setAdapter(adapter);
                /*mBinding.viewPager.setCurrentItem(pos);*/
                mBinding.viewPager.post(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.viewPager.setCurrentItem(pos);
                    }
                });
            }
        });
    }
}
