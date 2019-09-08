package obvious.assignment.nasaimagegallery.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import obvious.assignment.nasaimagegallery.BR;
import obvious.assignment.nasaimagegallery.R;
import obvious.assignment.nasaimagegallery.data.model.ImageDetails;
import obvious.assignment.nasaimagegallery.utility.RecyclerViewClickListener;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageListViewHolder> {

    List<ImageDetails> mImageDetailList;
    RecyclerViewClickListener mOnItemClickListener;

    public ImageListAdapter(List<ImageDetails> imageDetailList, RecyclerViewClickListener mOnItemClickListener) {
        this.mImageDetailList = imageDetailList;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public ImageListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.content_main, parent, false);
        return new ImageListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageListViewHolder holder, int position) {
        ImageDetails imageDetails = mImageDetailList.get(position);
        holder.bind(imageDetails);
    }

    @Override
    public int getItemCount() {
        return mImageDetailList.size();
    }

    public void setImageDetailList(List<ImageDetails> imageDetailList) {
        this.mImageDetailList = imageDetailList;
    }

    class ImageListViewHolder extends RecyclerView.ViewHolder {
        //Binding type attribute
        ViewDataBinding binding;
        public ImageListViewHolder(@NonNull ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            this.binding = dataBinding;
        }

        public void bind(ImageDetails imageDetails) {
            binding.setVariable(BR.imageDetails, imageDetails);
            binding.setVariable(BR.position, getAdapterPosition());
            binding.setVariable(BR.itemClickListener, mOnItemClickListener);
            binding.executePendingBindings();
        }
    }
}
