package obvious.assignment.nasaimagegallery.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import obvious.assignment.nasaimagegallery.data.model.ImageDetails;

@Dao
public interface ImageDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ImageDetails imageDetails);

    @Query("Select * from image_details order by date DESC")
    LiveData<List<ImageDetails>> getAllImages();
}
