package obvious.assignment.nasaimagegallery.utility;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class EndlessRecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

    //number of items in the dataset after the last load;
    private int mPreviousTotal = 0;

    //true if waiting for the last set to load
    private boolean mLoading = true;

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

        int visibleThreshold = 5;
        if(!mLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            //end has been reached
            onLoadMore();
            mLoading = true;
        }
    }

    public abstract void onLoadMore();
}
