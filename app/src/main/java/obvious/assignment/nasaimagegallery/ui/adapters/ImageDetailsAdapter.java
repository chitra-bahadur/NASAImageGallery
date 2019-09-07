package obvious.assignment.nasaimagegallery.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

import obvious.assignment.nasaimagegallery.R;
import obvious.assignment.nasaimagegallery.data.model.ImageDetails;

public class ImageDetailsAdapter extends PagerAdapter {

    private List<ImageDetails> imageDetailsList;

    public ImageDetailsAdapter(List<ImageDetails> imageDetailsList) {
        this.imageDetailsList = imageDetailsList;
    }


    @Override
    public int getCount() {
        return imageDetailsList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), R.layout.content_details, container, false);
        ImageDetails imageDetails = imageDetailsList.get(position);
        binding.setVariable(BR.imageDetails, imageDetails);
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}
