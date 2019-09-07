package obvious.assignment.nasaimagegallery.data.db;

import androidx.lifecycle.LiveData;

import java.util.List;

import obvious.assignment.nasaimagegallery.data.model.ImageDetails;

public class ImageDetailsLiveData extends LiveData<List<ImageDetails>> {

    private ImageDetails imageDetails;

}
